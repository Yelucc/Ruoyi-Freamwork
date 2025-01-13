package com.autoparams.business.loya.server.order.Strategy;


import com.autoparams.business.loya.domain.LoyaOrder;

public interface OrderProcessStrategy {
    Boolean process(LoyaOrder order);
}
