package com.autoparams.business.loya.server.order.Strategy;

import com.autoparams.business.loya.domain.LoyaOrder;
import com.autoparams.business.loya.enums.OrderStatus;

import com.autoparams.business.loya.server.jewel.ILoyaJewelCaseService;
import com.autoparams.business.loya.server.order.ILoyaOrderManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static com.autoparams.business.loya.enums.ReservationStatus.RESERVED;

@Component
public class LockedStrategy implements OrderProcessStrategy {
    @Autowired
    private ILoyaJewelCaseService caseService;
    @Autowired
    private ILoyaOrderManagementService orderManagementService;

    /**
     * @param order
     */
    @Override
    public Boolean process(LoyaOrder order) {
        // 查找关联单号下寄出方向的面单 要给这个物流补充单号，发件信息
        if (order.valid()) {
            Arrays.stream(order.getJewelCode())
                    .map(Long::parseLong)
                    .forEach(id ->
                            caseService.updateJewelStatus(id, RESERVED));
            order.setOrderStatus(OrderStatus.PENDING_SHIPMENT);
            return orderManagementService.updateById(order);
        }

        return false;
    }
}
