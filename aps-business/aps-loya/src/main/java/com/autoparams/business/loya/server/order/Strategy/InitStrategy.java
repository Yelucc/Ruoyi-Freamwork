package com.autoparams.business.loya.server.order.Strategy;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.autoparams.business.loya.domain.LoyaOrder;
import com.autoparams.business.loya.enums.OrderStatus;

import com.autoparams.business.loya.server.jewel.ILoyaJewelCaseService;
import com.autoparams.business.loya.server.order.ILoyaOrderManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static com.autoparams.business.loya.enums.ReservationStatus.RESERVED;

@Component
public class InitStrategy implements OrderProcessStrategy {
    @Autowired
    private ILoyaJewelCaseService caseService;
    @Autowired
    private ILoyaOrderManagementService orderManagementService;

    /**
     * 检查订单内容 是否满足创建条件
     * <p>
     * 订单状态创建 并锁定首饰
     *
     * @param order
     */
    @Override
    public Boolean process(LoyaOrder order) {
        if (order.valid()) {
            Arrays.stream(order.getJewelCode()).map(Long::parseLong).forEach(id -> caseService.updateJewelStatus(id, RESERVED));
            order.setOrderStatus(OrderStatus.LOCKED);
            return orderManagementService.updateById(order);
        } else {
            throw ExceptionUtil.wrapRuntime("信息缺失无法锁定订单");
        }
    }
}
