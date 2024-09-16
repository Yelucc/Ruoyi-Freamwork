package com.ruoyi.kuihua.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginBody;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.exception.user.CaptchaException;
import com.ruoyi.common.exception.user.CaptchaExpireException;
import com.ruoyi.common.exception.user.UserPasswordNotMatchException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.MessageUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.ip.IpUtils;
import com.ruoyi.framework.manager.AsyncManager;
import com.ruoyi.framework.manager.factory.AsyncFactory;
import com.ruoyi.framework.security.context.AuthenticationContextHolder;
import com.ruoyi.framework.web.service.SysLoginService;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.kuihua.domain.KhRegisterBody;
import com.ruoyi.kuihua.domain.KhTeam;
import com.ruoyi.kuihua.service.KhTeamService;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import com.ruoyi.kuihua.mapper.KhUserMapper;
import com.ruoyi.kuihua.domain.KhUser;
import com.ruoyi.kuihua.service.KhUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

/**
 * 用户管理Service业务层处理
 *
 * @author ruoyi
 * @date 2024-09-15
 */
@Service
public class KhUserServiceImpl extends ServiceImpl<KhUserMapper, KhUser>
        implements KhUserService {
    @Autowired
    private TokenService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;
    @Autowired
    private ISysUserService userService;
    @Autowired
    private SysLoginService loginService;
    @Autowired
    private ISysConfigService configService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private KhTeamService teamService;

    @Autowired
    private KhUserService khUserService;

    @Override
    public String Login(LoginBody body) {
        KhUser khUser = getOne(Wrappers.lambdaQuery(KhUser.class)
                .eq(KhUser::getPhone, body.getUsername())
                .or()
                .eq(KhUser::getWechatNumber, body.getUsername()));
        return loginService.login(khUser.getPhone(),body.getPassword(),body.getCode(),body.getUuid());
    }

    /**
     * 注册
     */
    @Override
    public String register(KhRegisterBody registerBody) {
        String msg = "", username = registerBody.getPhone(), password = registerBody.getPassword();
        SysUser sysUser = new SysUser();
        sysUser.setUserName(username);

        KhTeam khTeam = teamService.getOne(Wrappers.lambdaQuery(KhTeam.class).eq(KhTeam::getTeamCode, registerBody.getTeamCode()));
        if (khTeam == null) {
            msg = "团队不存在";
            throw new RuntimeException(msg);
        }

        // 验证码开关
        boolean captchaEnabled = configService.selectCaptchaEnabled();
        if (captchaEnabled) {
            validateCaptcha(username, registerBody.getCode(), registerBody.getUuid());
        }

        if (StringUtils.isEmpty(username)) {
            msg = "用户名不能为空";
        } else if (StringUtils.isEmpty(password)) {
            msg = "用户密码不能为空";
        } else if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH) {
            msg = "账户长度必须在2到20个字符之间";
        } else if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
            msg = "密码长度必须在5到20个字符之间";
        } else if (!userService.checkUserNameUnique(sysUser)) {
            msg = "保存用户'" + username + "'失败，注册账号已存在";
        } else {
            sysUser.setNickName(username);
            sysUser.setPassword(SecurityUtils.encryptPassword(password));
            boolean regFlag = userService.registerUser(sysUser);

            sysUser.setDeptId(khTeam.getDeptId());
            sysUser.setRoleIds(new Long[]{101L});

            userService.updateUser(sysUser);

            KhUser khUser = new KhUser();
            khUser.setSysUserId(sysUser.getUserId());
            khUser.setNickName(registerBody.getPhone());
            khUser.setTeamId(khTeam.getTeamId());
            khUser.setPhone(registerBody.getPhone());
            khUser.setWechatNumber(registerBody.getWechatNumber());
            khUserService.save(khUser);

            if (!regFlag) {
                msg = "注册失败,请联系系统管理人员";
            } else {
                // 用户验证
                Authentication authentication = null;
                try
                {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
                    AuthenticationContextHolder.setContext(authenticationToken);
                    // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
                    authentication = authenticationManager.authenticate(authenticationToken);
                }
                catch (Exception e)
                {
                    if (e instanceof BadCredentialsException)
                    {
                        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
                        throw new UserPasswordNotMatchException();
                    }
                    else
                    {
                        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, e.getMessage()));
                        throw new ServiceException(e.getMessage());
                    }
                }
                finally
                {
                    AuthenticationContextHolder.clearContext();
                }
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
                LoginUser loginUser = (LoginUser) authentication.getPrincipal();
                recordLoginInfo(loginUser.getUserId());
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username,
                        Constants.REGISTER, MessageUtils.message("user.register.success")));
                // 生成token
                return tokenService.createToken(loginUser);
            }
        }
        throw new RuntimeException(msg);
    }
    /**
     * 记录登录信息
     *
     * @param userId 用户ID
     */
    public void recordLoginInfo(Long userId)
    {
        SysUser sysUser = new SysUser();
        sysUser.setUserId(userId);
        sysUser.setLoginIp(IpUtils.getIpAddr());
        sysUser.setLoginDate(DateUtils.getNowDate());
        userService.updateUserProfile(sysUser);
    }
    /**
     * 校验验证码
     *
     * @param username 用户名
     * @param code     验证码
     * @param uuid     唯一标识
     * @return 结果
     */
    private void validateCaptcha(String username, String code, String uuid) {
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + StringUtils.nvl(uuid, "");
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteObject(verifyKey);
        if (captcha == null) {
            throw new CaptchaExpireException();
        }
        if (!code.equalsIgnoreCase(captcha)) {
            throw new CaptchaException();
        }
    }
}
