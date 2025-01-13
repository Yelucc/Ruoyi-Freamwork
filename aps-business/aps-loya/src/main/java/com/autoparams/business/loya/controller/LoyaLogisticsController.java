package com.autoparams.business.loya.controller;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.autoparams.business.loya.domain.LoyaLogistics;
import com.autoparams.business.loya.domain.LoyaOrder;
import com.autoparams.business.loya.enums.OrderStatus;
import com.autoparams.business.loya.server.logistics.ILoyaLogisticsService;
import com.autoparams.business.loya.server.order.ILoyaOrderManagementService;
import com.autoparams.common.base.annotation.Log;
import com.autoparams.common.base.core.controller.BaseController;
import com.autoparams.common.base.core.domain.AjaxResult;
import com.autoparams.common.base.core.page.TableDataInfo;
import com.autoparams.common.base.enums.BusinessType;
import com.autoparams.common.base.utils.SecurityUtils;
import com.autoparams.common.base.utils.poi.ExcelUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 物流管理Controller
 *
 * @author Yelucc
 * @date 2024-08-10
 */
@RestController
@RequestMapping("/logistics/logistics")
public class LoyaLogisticsController extends BaseController {
    @Autowired
    private ILoyaLogisticsService loyaLogisticsService;

    @Autowired
    private ILoyaOrderManagementService orderManagementService;
    /**
     * 查询物流管理列表
     */
    @PreAuthorize("@ss.hasPermi('logistics:logistics:list')")
    @GetMapping("/list")
    public TableDataInfo list(LoyaLogistics loyaLogistics) {
        startPage();
        List<LoyaLogistics> list = loyaLogisticsService.list(Wrappers.lambdaQuery(loyaLogistics));
        return getDataTable(list);
    }

    /**
     * 导出物流管理列表
     */
    @PreAuthorize("@ss.hasPermi('logistics:logistics:export')")
    @Log(title = "物流管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, LoyaLogistics loyaLogistics) {
        List<LoyaLogistics> list = loyaLogisticsService.list(Wrappers.lambdaQuery(loyaLogistics));
        ExcelUtil<LoyaLogistics> util = new ExcelUtil<LoyaLogistics>(LoyaLogistics.class);
        util.exportExcel(response, list, "物流管理数据");
    }

    /**
     * 获取物流管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('logistics:logistics:query')")
    @GetMapping(value = "/{trackingNumber}")
    public AjaxResult getInfo(@PathVariable("trackingNumber") Long trackingNumber) {
        return success(loyaLogisticsService.getById(trackingNumber));
    }

    @PreAuthorize("@ss.hasPermi('logistics:logistics:list')")
    @GetMapping("/history")
    public TableDataInfo userList() {
        List<LoyaLogistics> list = loyaLogisticsService.list(Wrappers.lambdaQuery(LoyaLogistics.class)
                .eq(LoyaLogistics::getCreateBy, SecurityUtils.getLoginUser().getUser().getUserId())
        ).stream().distinct().collect(Collectors.toList());
        return getDataTable(list.stream().map(LoyaLogistics::vantAddress).collect(Collectors.toList()));
    }

    /**
     * 新增物流管理
     */
    @PreAuthorize("@ss.hasPermi('logistics:logistics:add')")
    @Log(title = "物流管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody LoyaLogistics loyaLogistics) {
        return toAjax(loyaLogisticsService.save(loyaLogistics));
    }


    /**
     * 修改物流管理
     */
    @PreAuthorize("@ss.hasPermi('logistics:logistics:edit')")
    @Log(title = "物流管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody LoyaLogistics loyaLogistics) {

        if (ArrayUtil.isNotEmpty(ArrayUtil.filter(loyaLogistics.getExceptJewelIds(),
                (ele)->!StrUtil.isBlankIfStr(ele)))){
            loyaLogistics.setConfirmStatus("Except");
            // 获取订单把状态回退
            Long orderId = loyaLogistics.getOrderId();
            orderManagementService.lambdaUpdate()
                    .eq(LoyaOrder::getOrderId,orderId)
                    .set(LoyaOrder::getOrderStatus, OrderStatus.RECEIVED_AWAITING_RETURN)
                    .update();
        }else if ("Pass".equals(loyaLogistics.getConfirmStatus())){
            Long orderId = loyaLogistics.getOrderId();
            LoyaOrder order = orderManagementService.getById(orderId);
            order.setOrderStatus(OrderStatus.RETURN_AWAITING_CHECK);
            orderManagementService.updateOrder(order);
        }

        return toAjax(loyaLogisticsService.updateById(loyaLogistics));
    }

    /**
     * 删除物流管理
     */
    @PreAuthorize("@ss.hasPermi('logistics:logistics:remove')")
    @Log(title = "物流管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{trackingNumbers}")
    public AjaxResult remove(@PathVariable Long[] trackingNumbers) {
        return toAjax(loyaLogisticsService.removeBatchByIds(Arrays.asList(trackingNumbers)));
    }
}
