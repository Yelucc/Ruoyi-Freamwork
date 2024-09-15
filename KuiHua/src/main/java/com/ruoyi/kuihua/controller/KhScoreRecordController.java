package com.ruoyi.kuihua.controller;

import java.util.Arrays;
import java.util.List;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import javax.servlet.http.HttpServletResponse;
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
import com.ruoyi.kuihua.domain.KhScoreRecord;
import com.ruoyi.kuihua.service.KhScoreRecordService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 葵花分数记录Controller
 *
 * @author ruoyi
 * @date 2024-09-15
 */
@RestController
@RequestMapping("/KuiHua/score-record")
public class KhScoreRecordController extends BaseController
{
    @Autowired
    private KhScoreRecordService khScoreRecordService;

    /**
     * 查询葵花分数记录列表
     */
    @PreAuthorize("@ss.hasPermi('KuiHua:score-record:list')")
    @GetMapping("/list")
    public TableDataInfo list(KhScoreRecord khScoreRecord)
    {
        Page<KhScoreRecord> list = khScoreRecordService.page(getPage(), Wrappers.lambdaQuery(khScoreRecord));
        return getDataTable(list);
    }

    /**
     * 导出葵花分数记录列表
     */
    @PreAuthorize("@ss.hasPermi('KuiHua:score-record:export')")
    @Log(title = "葵花分数记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, KhScoreRecord khScoreRecord)
    {
        List<KhScoreRecord> list = khScoreRecordService.list(Wrappers.lambdaQuery(khScoreRecord));
        ExcelUtil<KhScoreRecord> util = new ExcelUtil<KhScoreRecord>(KhScoreRecord.class);
        util.exportExcel(response, list, "葵花分数记录数据");
    }

    /**
     * 获取葵花分数记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('KuiHua:score-record:query')")
    @GetMapping(value = "/{recordId}")
    public AjaxResult getInfo(@PathVariable("recordId") Long recordId)
    {
        return success(khScoreRecordService.getById(recordId));
    }

    /**
     * 新增葵花分数记录
     */
    @PreAuthorize("@ss.hasPermi('KuiHua:score-record:add')")
    @Log(title = "葵花分数记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody KhScoreRecord khScoreRecord)
    {
        return toAjax(khScoreRecordService.save(khScoreRecord));
    }

    /**
     * 修改葵花分数记录
     */
    @PreAuthorize("@ss.hasPermi('KuiHua:score-record:edit')")
    @Log(title = "葵花分数记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody KhScoreRecord khScoreRecord)
    {
        return toAjax(khScoreRecordService.updateById(khScoreRecord));
    }

    /**
     * 删除葵花分数记录
     */
    @PreAuthorize("@ss.hasPermi('KuiHua:score-record:remove')")
    @Log(title = "葵花分数记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{recordIds}")
    public AjaxResult remove(@PathVariable Long[] recordIds)
    {
        return toAjax(khScoreRecordService.removeBatchByIds(Arrays.asList(recordIds)));
    }
}
