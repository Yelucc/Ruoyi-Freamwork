package com.ruoyi.kuihua.controller;

import java.util.Arrays;
import java.util.List;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.annotation.Anonymous;
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
import com.ruoyi.kuihua.domain.KhTeam;
import com.ruoyi.kuihua.service.KhTeamService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 团队管理Controller
 *
 * @author ruoyi
 * @date 2024-09-15
 */
@RestController
@RequestMapping("/KuiHua/team")
public class KhTeamController extends BaseController {
    @Autowired
    private KhTeamService khTeamService;

    /**
     * 查询团队管理列表
     */
    @PreAuthorize("@ss.hasPermi('KuiHua:team:list')")
    @GetMapping("/list")
    public TableDataInfo list(KhTeam khTeam) {
        Page<KhTeam> list = khTeamService.page(getPage(), Wrappers.lambdaQuery(khTeam));
        return getDataTable(list);
    }

    /**
     * 查询团队排行榜
     */
    @Anonymous
    @GetMapping("/leaderboard")
    public TableDataInfo getLeaderBoard() {
        return getDataTable(khTeamService.getLeaderBoardVoList());
    }

    /**
     * 导出团队管理列表
     */
    @PreAuthorize("@ss.hasPermi('KuiHua:team:export')")
    @Log(title = "团队管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, KhTeam khTeam) {
        List<KhTeam> list = khTeamService.list(Wrappers.lambdaQuery(khTeam));
        ExcelUtil<KhTeam> util = new ExcelUtil<KhTeam>(KhTeam.class);
        util.exportExcel(response, list, "团队管理数据");
    }

    /**
     * 获取团队管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('KuiHua:team:query')")
    @GetMapping(value = "/{teamId}")
    public AjaxResult getInfo(@PathVariable("teamId") Long teamId) {
        return success(khTeamService.getById(teamId));
    }

    /**
     * 新增团队管理
     */
    @PreAuthorize("@ss.hasPermi('KuiHua:team:add')")
    @Log(title = "团队管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody KhTeam khTeam) {
        return toAjax(khTeamService.save(khTeam));
    }

    /**
     * 修改团队管理
     */
    @PreAuthorize("@ss.hasPermi('KuiHua:team:edit')")
    @Log(title = "团队管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody KhTeam khTeam) {
        return toAjax(khTeamService.updateById(khTeam));
    }

    /**
     * 删除团队管理
     */
    @PreAuthorize("@ss.hasPermi('KuiHua:team:remove')")
    @Log(title = "团队管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{teamIds}")
    public AjaxResult remove(@PathVariable Long[] teamIds) {
        return toAjax(khTeamService.removeBatchByIds(Arrays.asList(teamIds)));
    }
}
