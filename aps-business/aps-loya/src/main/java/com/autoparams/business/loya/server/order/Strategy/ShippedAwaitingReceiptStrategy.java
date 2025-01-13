package com.autoparams.business.loya.server.order.Strategy;

import com.autoparams.business.loya.domain.LoyaLogistics;
import com.autoparams.business.loya.domain.LoyaOrder;
import com.autoparams.business.loya.enums.OrderStatus;
import com.autoparams.business.loya.server.logistics.ILoyaLogisticsService;
import com.autoparams.business.loya.server.order.ILoyaOrderManagementService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ShippedAwaitingReceiptStrategy implements OrderProcessStrategy {
    @Autowired
    private ILoyaOrderManagementService orderManagementService;
    @Autowired
    private ILoyaLogisticsService logisticsService;

    /**
     * @param order
     */
    @Override
    public Boolean process(LoyaOrder order) {
        // 通过定时任务 调度物流API 或前期先手动补充状态 将
        // 如：确认物流信息，更新订单状态为 "已签收待寄回"
        LoyaLogistics logistics = logisticsService
                .getOne(Wrappers.lambdaQuery(LoyaLogistics.class)
                        .eq(LoyaLogistics::getOrderId, order.getOrderId())
                        .eq(LoyaLogistics::getDirection, "trip")
                );
        logistics.setRecipientTime(new Date());
        logisticsService.updateById(logistics);

        order.setOrderStatus(OrderStatus.RECEIVED_AWAITING_RETURN);
        // 其他相关逻辑
        return orderManagementService.updateById(order);
    }
}
