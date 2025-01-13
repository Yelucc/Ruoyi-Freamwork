package com.autoparams.business.loya.server.order.Strategy;

import com.autoparams.business.loya.enums.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderProcessStrategyFactory {
    private final Map<OrderStatus, OrderProcessStrategy> strategies;

    @Autowired
    public OrderProcessStrategyFactory(List<OrderProcessStrategy> strategyList) {
        strategies = new EnumMap<>(OrderStatus.class);
        strategyList.forEach(strategy -> {
            if (strategy instanceof PendingShipmentStrategy) {
                strategies.put(OrderStatus.PENDING_SHIPMENT, strategy);
            } else if (strategy instanceof ShippedAwaitingReceiptStrategy) {
                strategies.put(OrderStatus.SHIPPED_AWAITING_RECEIPT, strategy);
            } else if (strategy instanceof ReceivedAwaitingReturnStrategy) {
                strategies.put(OrderStatus.RECEIVED_AWAITING_RETURN, strategy);
            } else if (strategy instanceof ReturnedStrategy) {
                strategies.put(OrderStatus.RETURNED, strategy);
            } else if (strategy instanceof LockedStrategy) {
                strategies.put(OrderStatus.LOCKED, strategy);
            }else if (strategy instanceof InitStrategy) {
                strategies.put(OrderStatus.INIT, strategy);
            }else if (strategy instanceof ReturnAwaitingCheckStrategy) {
                strategies.put(OrderStatus.RETURN_AWAITING_CHECK, strategy);
            }
        });
    }

    public OrderProcessStrategy getStrategy(OrderStatus status) {
        return strategies.get(status);
    }
}
