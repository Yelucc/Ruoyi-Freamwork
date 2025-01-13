package com.autoparams.business.loya.server.order.Strategy;

import com.autoparams.business.loya.domain.LoyaLogistics;
import com.autoparams.business.loya.domain.LoyaOrder;
import com.autoparams.business.loya.enums.OrderStatus;
import com.autoparams.business.loya.server.jewel.ILoyaJewelCaseService;
import com.autoparams.business.loya.server.logistics.ILoyaLogisticsService;
import com.autoparams.business.loya.server.order.ILoyaOrderManagementService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReceivedAwaitingReturnStrategy implements OrderProcessStrategy {
    @Autowired
    private ILoyaLogisticsService logisticsService;
    @Autowired
    private ILoyaOrderManagementService orderManagementService;

    @Autowired
    private ILoyaJewelCaseService caseService;

    /**
     * @param order
     */
    @Override
    public Boolean process(LoyaOrder order) {
        // 可能又分批寄回的可能 所以要检查这个订单所有寄返的物流 看是不是寄回全
        List<LoyaLogistics> logistics = logisticsService
                .list(Wrappers.lambdaQuery(LoyaLogistics.class)
                        .eq(LoyaLogistics::getOrderId, order.getOrderId())
                        .eq(LoyaLogistics::getDirection, "turn")
                );
        List<String> trips = Arrays.asList(order.getJewelCode());
        List<String> turns = logistics.stream()
                .map(LoyaLogistics::getJewelIds)
                .flatMap(Arrays::stream)
                .collect(Collectors.toList());
//        turns.stream().map(Long::parseLong)
//                .forEach(id ->
//                        caseService.updateJewelStatus(id, LoyaJewel.ReservationStatusEnum.AVAILABLE));


        if (new HashSet<>(turns).containsAll(trips)) {
            order.setOrderStatus(OrderStatus.RETURN_AWAITING_CHECK);
            return orderManagementService.updateById(order);
//            return true;
        } else {
            return true;
//            throw ExceptionUtil.wrapRuntime("首饰未完全寄回");
        }


    }
}
