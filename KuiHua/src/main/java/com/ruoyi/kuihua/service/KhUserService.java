package com.ruoyi.kuihua.service;

import java.util.List;

import com.ruoyi.common.core.domain.model.RegisterBody;
import com.ruoyi.kuihua.domain.KhUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 用户管理Service接口
 *
 * @author ruoyi
 * @date 2024-09-15
 */
public interface KhUserService extends IService<KhUser> {
    String register(RegisterBody registerBody, String teamCode);

}
