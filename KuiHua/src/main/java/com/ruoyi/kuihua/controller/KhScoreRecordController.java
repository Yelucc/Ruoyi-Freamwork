package com.ruoyi.kuihua.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.kuihua.domain.KhScoreRecord;
import com.ruoyi.kuihua.service.KhScoreRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

/**
 * 葵花分数记录Controller
 *
 * @author ruoyi
 * @date 2024-09-15
 */
@RestController
@RequestMapping("/KuiHua/scoreRecord")
public class KhScoreRecordController extends BaseController {
    @Autowired
    private KhScoreRecordService khScoreRecordService;


    /**
     * 移动端用户上传葵花分数记录
     */
    @PreAuthorize("@ss.hasPermi('KuiHua:scoreRecord:add')")
    @Log(title = "葵花分数记录", businessType = BusinessType.INSERT)
    @PostMapping("/submit")
    public AjaxResult submit(@RequestParam("sharedLink") String sharedLink, @RequestParam("sharedPicture") MultipartFile[] sharedPicture) throws NoSuchAlgorithmException, IOException {
        return success(khScoreRecordService.submitShareRecord(sharedLink, sharedPicture));
    }


    /**
     * 查询葵花分数记录列表
     */
    @PreAuthorize("@ss.hasPermi('KuiHua:scoreRecord:list')")
    @GetMapping("/list")
    public TableDataInfo list(KhScoreRecord khScoreRecord) {
        Page<KhScoreRecord> list = khScoreRecordService.page(getPage(), Wrappers.lambdaQuery(khScoreRecord));
        return getDataTable(list);
    }

    /**
     * 导出葵花分数记录列表
     */
    @PreAuthorize("@ss.hasPermi('KuiHua:scoreRecord:export')")
    @Log(title = "葵花分数记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, KhScoreRecord khScoreRecord) {
        List<KhScoreRecord> list = khScoreRecordService.list(Wrappers.lambdaQuery(khScoreRecord));
        ExcelUtil<KhScoreRecord> util = new ExcelUtil<KhScoreRecord>(KhScoreRecord.class);
        util.exportExcel(response, list, "葵花分数记录数据");
    }

    /**
     * 获取葵花分数记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('KuiHua:scoreRecord:query')")
    @GetMapping(value = "/{recordId}")
    public AjaxResult getInfo(@PathVariable("recordId") Long recordId) {
        return success(khScoreRecordService.getById(recordId));
    }

    /**
     * 新增葵花分数记录
     */
    @PreAuthorize("@ss.hasPermi('KuiHua:scoreRecord:add')")
    @Log(title = "葵花分数记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody KhScoreRecord khScoreRecord) {
        return toAjax(khScoreRecordService.save(khScoreRecord));
    }

    /**
     * 修改葵花分数记录
     */
    @PreAuthorize("@ss.hasPermi('KuiHua:scoreRecord:edit')")
    @Log(title = "葵花分数记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody KhScoreRecord khScoreRecord) {
        return toAjax(khScoreRecordService.changeRecordStatus(khScoreRecord));
    }

    /**
     * 删除葵花分数记录
     */
    @PreAuthorize("@ss.hasPermi('KuiHua:scoreRecord:remove')")
    @Log(title = "葵花分数记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{recordIds}")
    public AjaxResult remove(@PathVariable Long[] recordIds) {
        return toAjax(khScoreRecordService.removeBatchByIds(Arrays.asList(recordIds)));
    }
}
