package com.autoparams.web.controller.business.loya;

import com.autoparams.business.loya.domain.LoyaJewel;
import com.autoparams.business.loya.domain.LoyaLogistics;
import com.autoparams.business.loya.domain.LoyaOrder;
import com.autoparams.business.loya.domain.vo.LoyaOrderReserveVo;
import com.autoparams.business.loya.domain.vo.LoyaOrderVo;
import com.autoparams.business.loya.server.jewel.ILoyaJewelCaseService;
import com.autoparams.business.loya.server.logistics.ILoyaLogisticsService;
import com.autoparams.business.loya.server.order.ILoyaOrderManagementService;
import com.autoparams.common.base.annotation.Log;
import com.autoparams.common.base.core.controller.BaseController;
import com.autoparams.common.base.core.domain.AjaxResult;
import com.autoparams.common.base.core.page.TableDataInfo;
import com.autoparams.common.base.enums.BusinessType;
import com.autoparams.common.base.utils.poi.ExcelUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 订单管理Controller
 *
 * @author ruoyi
 * @date 2024-08-09
 */
@RestController
@RequestMapping("/order/orderManagement")
public class LoyaOrderManagementController extends BaseController {
    @Autowired
    private ILoyaOrderManagementService loyaOrderManagementService;
    @Autowired
    private ILoyaJewelCaseService caseService;
    @Autowired
    private ILoyaLogisticsService logisticsService;

    /**
     * 查询订单管理列表
     */
    @PreAuthorize("@ss.hasPermi('order:orderManagement:list')")
    @GetMapping("/list")
    public TableDataInfo list(LoyaOrder loyaOrder) {
        Page<LoyaOrder> list = loyaOrderManagementService.page(getPage(), Wrappers.lambdaQuery(loyaOrder));
        list.setRecords(list.getRecords().stream()
                .peek(item -> item.setJewels(caseService.list(Wrappers.lambdaQuery(LoyaJewel.class)
                        .in(LoyaJewel::getJewelId, Arrays.asList(item.getJewelCode())))))
                .collect(Collectors.toList()));
        return getDataTable(list);
    }

    /**
     * 查询订单管理列表
     */
    @PreAuthorize("@ss.hasPermi('order:orderManagement:list')")
    @GetMapping("/list-vo")
    public TableDataInfo listVo(LoyaOrderVo loyaOrder) {
        Page<LoyaOrderVo> list = loyaOrderManagementService.listVo(getPage(), loyaOrder);
        return getDataTable(list);
    }

    /**
     * 导出订单管理列表
     */
    @PreAuthorize("@ss.hasPermi('order:orderManagement:export')")
    @Log(title = "订单管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, LoyaOrder loyaOrder) {
        List<LoyaOrder> list = loyaOrderManagementService.list(Wrappers.lambdaQuery(loyaOrder));
        ExcelUtil<LoyaOrder> util = new ExcelUtil<LoyaOrder>(LoyaOrder.class);
        util.exportExcel(response, list, "订单管理数据");
    }

    /**
     * 获取订单管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('order:orderManagement:query')")
    @GetMapping(value = "/{orderId}")
    public AjaxResult getInfo(@PathVariable("orderId") Long orderId) {
        LoyaOrder loyaOrder = loyaOrderManagementService.getById(orderId);
        loyaOrder.setJewels(caseService.list(Wrappers.lambdaQuery(LoyaJewel.class)
                .in(LoyaJewel::getJewelId, Arrays.asList(loyaOrder.getJewelCode()))));

        loyaOrder.setTripInfo(logisticsService.getOne(Wrappers.lambdaQuery(LoyaLogistics.class)
                .eq(LoyaLogistics::getDirection, "trip")
                .eq(LoyaLogistics::getOrderId, orderId)));
        loyaOrder.setTurnInfo(logisticsService.list(Wrappers.lambdaQuery(LoyaLogistics.class)
                .eq(LoyaLogistics::getDirection, "turn")
                .eq(LoyaLogistics::getOrderId, orderId)));
        return success(loyaOrder);
    }

    /**
     * 新增订单管理
     */
    @PreAuthorize("@ss.hasPermi('order:orderManagement:add')")
    @Log(title = "订单管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult createOrder(@RequestBody LoyaOrderReserveVo loyaOrder) {
        return toAjax(loyaOrderManagementService.createOrder(loyaOrder));
    }


    /**
     * 修改订单管理
     */
    @PreAuthorize("@ss.hasPermi('order:orderManagement:edit')")
    @Log(title = "订单管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody LoyaOrder loyaOrder) {




        return toAjax(loyaOrderManagementService.updateOrder(loyaOrder));
    }

    /**
     * 删除订单管理
     */
    @PreAuthorize("@ss.hasPermi('order:orderManagement:remove')")
    @Log(title = "订单管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{orderIds}")
    public AjaxResult remove(@PathVariable Long[] orderIds) {
        return toAjax(loyaOrderManagementService.closeOrder(orderIds));
    }
}
