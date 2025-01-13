package com.autoparams.business.loya.server.order.Strategy;

import com.autoparams.business.loya.domain.LoyaOrder;
import com.autoparams.business.loya.enums.OrderStatus;

import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LoyaOrderUpdateGroupSequenceProvider implements DefaultGroupSequenceProvider<LoyaOrder> {
    /**
     * @param loyaOrder
     * @return
     */
    @Override
    public List<Class<?>> getValidationGroups(LoyaOrder loyaOrder) {
        List<Class<?>> defaultGroupSequence = new ArrayList<>();
        defaultGroupSequence.add(LoyaOrder.class);  // 默认的组

        if (loyaOrder != null) {
            OrderStatus status = loyaOrder.getOrderStatus();
            if (Objects.isNull(status)) {
                defaultGroupSequence.add(LoyaOrder.Init.class);
            } else {
                switch (status) {
                    case LOCKED:
                        defaultGroupSequence.add(LoyaOrder.Locked.class);
                        break;
                    case PENDING_SHIPMENT:
                        defaultGroupSequence.add(LoyaOrder.PendingShipment.class);
                        break;
                    case SHIPPED_AWAITING_RECEIPT:
                        defaultGroupSequence.add(LoyaOrder.Shipped.class);
                        break;
                    case RECEIVED_AWAITING_RETURN:
                        defaultGroupSequence.add(LoyaOrder.Received.class);
                        break;
                    case RETURNED:
                        defaultGroupSequence.add(LoyaOrder.Returned.class);
                        break;
                    default:
                        defaultGroupSequence.add(LoyaOrder.Init.class);
                        break;
                }
            }
        }

        return defaultGroupSequence;
    }
}
