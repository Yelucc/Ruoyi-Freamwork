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
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static com.autoparams.business.loya.enums.ReservationStatus.AVAILABLE;

@Component
public class ReturnAwaitingCheckStrategy implements OrderProcessStrategy {
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
        // 如果发生异常，物流订单会被标记为为异常，不能更新某个首饰的状态
        List<LoyaLogistics> logistics = logisticsService
                .list(Wrappers.lambdaQuery(LoyaLogistics.class)
                        .eq(LoyaLogistics::getOrderId, order.getOrderId())
                        .eq(LoyaLogistics::getDirection, "turn")
                );

        List<String> trips = Arrays.asList(order.getJewelCode());
        // 排除异常件
        List<String> turns = logistics.stream()
                .map(LoyaLogistics::getPassJewelIds)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        turns.stream().map(Long::parseLong)
                .forEach(id ->
                        caseService.updateJewelStatus(id, AVAILABLE));
        if (new HashSet<>(turns).containsAll(trips)) {
            order.setOrderStatus(OrderStatus.RETURNED);
            return orderManagementService.updateById(order);
//            return true;
        } else {
            return true;
//            throw ExceptionUtil.wrapRuntime("首饰未完全寄回");
        }


    }
}
