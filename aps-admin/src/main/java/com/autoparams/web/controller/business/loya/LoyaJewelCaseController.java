package com.autoparams.web.controller.business.loya;

import com.autoparams.business.loya.domain.LoyaJewel;
import com.autoparams.business.loya.enums.ReservationStatus;
import com.autoparams.business.loya.server.jewel.ILoyaJewelCaseService;
import com.autoparams.common.base.annotation.Log;
import com.autoparams.common.base.core.controller.BaseController;
import com.autoparams.common.base.core.domain.AjaxResult;
import com.autoparams.common.base.core.page.TableDataInfo;
import com.autoparams.common.base.enums.BusinessType;
import com.autoparams.common.base.utils.poi.ExcelUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * 首饰管理Controller
 *
 * @author Yeeluc
 * @date 2024-08-09
 */
@RestController
@RequestMapping("/jewelcase")
public class LoyaJewelCaseController extends BaseController {
    @Autowired
    private ILoyaJewelCaseService loyaJewelCaseService;

    /**
     * 查询首饰管理列表
     */
    @PreAuthorize("@ss.hasPermi('jewel:jewelcase:list')")
    @GetMapping("/list")
    public TableDataInfo list(LoyaJewel loyaJewel) {
        startPage();
        List<LoyaJewel> list = loyaJewelCaseService.list(Wrappers.lambdaQuery(loyaJewel));
        return getDataTable(list);
    }

    /**
     * 导出首饰管理列表
     */
    @PreAuthorize("@ss.hasPermi('jewel:jewelcase:export')")
    @Log(title = "首饰管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, LoyaJewel loyaJewel) {
        List<LoyaJewel> list = loyaJewelCaseService.list(Wrappers.lambdaQuery(loyaJewel));
        ExcelUtil<LoyaJewel> util = new ExcelUtil<>(LoyaJewel.class);
        util.exportExcel(response, list, "首饰管理数据");
    }

    /**
     * 获取首饰管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('jewel:jewelcase:query')")
    @GetMapping(value = "/{jewelId}")
    public AjaxResult getInfo(@PathVariable("jewelId") Long jewelId) {
        return success(loyaJewelCaseService.getOptById(jewelId));
    }

    /**
     * 新增首饰管理
     */
    @PreAuthorize("@ss.hasPermi('jewel:jewelcase:add')")
    @Log(title = "首饰管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody LoyaJewel loyaJewel) {
        loyaJewel.setReservationStatus(ReservationStatus.AVAILABLE);
        return toAjax(loyaJewelCaseService.save(loyaJewel));
    }

    /**
     * 修改首饰管理
     */
    @PreAuthorize("@ss.hasPermi('jewel:jewelcase:edit')")
    @Log(title = "首饰管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody LoyaJewel loyaJewel) {
        return toAjax(loyaJewelCaseService.updateById(loyaJewel));
    }

    /**
     * 删除首饰管理
     */
    @PreAuthorize("@ss.hasPermi('jewel:jewelcase:remove')")
    @Log(title = "首饰管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{jewelIds}")
    public AjaxResult remove(@PathVariable Long[] jewelIds) {
        return toAjax(loyaJewelCaseService.removeBatchByIds(Arrays.asList(jewelIds)));
    }
}
