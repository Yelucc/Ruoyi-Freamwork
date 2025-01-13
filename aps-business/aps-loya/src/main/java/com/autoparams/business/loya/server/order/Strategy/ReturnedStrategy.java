package com.autoparams.business.loya.server.order.Strategy;

import com.autoparams.business.loya.domain.LoyaOrder;
import com.autoparams.business.loya.enums.OrderStatus;

import com.autoparams.business.loya.server.jewel.ILoyaJewelCaseService;
import com.autoparams.business.loya.server.order.ILoyaOrderManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReturnedStrategy implements OrderProcessStrategy {

    @Autowired
    private ILoyaJewelCaseService caseService;
    @Autowired
    private ILoyaOrderManagementService orderManagementService;

    /**
     * @param order
     */
    @Override
    public Boolean process(LoyaOrder order) {
        // 验证反馈内容 并结束订单
        if (order.valid()) {
            order.setOrderStatus(OrderStatus.CLOSE);
            return orderManagementService.updateById(order);
        }
        return false;
    }
}
