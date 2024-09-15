package com.ruoyi.kuihua.controller;

import java.util.Arrays;
import java.util.List;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.domain.model.RegisterBody;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.kuihua.domain.KhRegisterBody;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.kuihua.domain.KhUser;
import com.ruoyi.kuihua.service.KhUserService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户管理Controller
 *
 * @author ruoyi
 * @date 2024-09-15
 */
@RestController
@RequestMapping("/KuiHua/khuser")
public class KhUserController extends BaseController {
    @Autowired
    private KhUserService khUserService;


    @Anonymous
    @PostMapping("/register")
    public AjaxResult register(@RequestBody KhRegisterBody user) {
        String msg = khUserService.register(user);
        return StringUtils.isEmpty(msg) ? success() : error(msg);
    }

    /**
     * 查询用户管理列表
     */
    @PreAuthorize("@ss.hasPermi('KuiHua:khuser:list')")
    @GetMapping("/list")
    public TableDataInfo list(KhUser khUser) {
        Page<KhUser> list = khUserService.page(getPage(), Wrappers.lambdaQuery(khUser));
        return getDataTable(list);
    }

    /**
     * 导出用户管理列表
     */
    @PreAuthorize("@ss.hasPermi('KuiHua:khuser:export')")
    @Log(title = "用户管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, KhUser khUser) {
        List<KhUser> list = khUserService.list(Wrappers.lambdaQuery(khUser));
        ExcelUtil<KhUser> util = new ExcelUtil<KhUser>(KhUser.class);
        util.exportExcel(response, list, "用户管理数据");
    }

    /**
     * 获取用户管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('KuiHua:khuser:query')")
    @GetMapping(value = "/{userId}")
    public AjaxResult getInfo(@PathVariable("userId") Long userId) {
        return success(khUserService.getById(userId));
    }

    /**
     * 新增用户管理
     */
    @PreAuthorize("@ss.hasPermi('KuiHua:khuser:add')")
    @Log(title = "用户管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody KhUser khUser) {
        return toAjax(khUserService.save(khUser));
    }

    /**
     * 修改用户管理
     */
    @PreAuthorize("@ss.hasPermi('KuiHua:khuser:edit')")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody KhUser khUser) {
        return toAjax(khUserService.updateById(khUser));
    }

    /**
     * 删除用户管理
     */
    @PreAuthorize("@ss.hasPermi('KuiHua:khuser:remove')")
    @Log(title = "用户管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{userIds}")
    public AjaxResult remove(@PathVariable Long[] userIds) {
        return toAjax(khUserService.removeBatchByIds(Arrays.asList(userIds)));
    }
}
