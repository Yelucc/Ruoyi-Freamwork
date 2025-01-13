package com.autoparams.business.loya.server.order.Strategy;

import com.autoparams.business.loya.domain.LoyaLogistics;
import com.autoparams.business.loya.domain.LoyaOrder;
import com.autoparams.business.loya.enums.OrderStatus;
import com.autoparams.business.loya.server.jewel.ILoyaJewelCaseService;
import com.autoparams.business.loya.server.logistics.ILoyaLogisticsService;
import com.autoparams.business.loya.server.order.ILoyaOrderManagementService;
import com.autoparams.common.base.utils.bean.BeanUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;

import static com.autoparams.business.loya.enums.ReservationStatus.TRIP;

@Component
public class PendingShipmentStrategy implements OrderProcessStrategy {
    @Autowired
    private ILoyaJewelCaseService caseService;
    @Autowired
    private ILoyaOrderManagementService orderManagementService;
    @Autowired
    private ILoyaLogisticsService logisticsService;

    /**
     * @param order
     */
    @Override
    public Boolean process(LoyaOrder order) {
        // 更新订单状态为 "已寄出待签收"
        // 已确认 待寄出 补充相应信息
        if (order.valid()) {
            LoyaLogistics logistics = logisticsService
                    .getOne(Wrappers.lambdaQuery(LoyaLogistics.class)
                            .eq(LoyaLogistics::getOrderId, order.getOrderId())
                            .eq(LoyaLogistics::getDirection, "trip")
                    );
            LoyaLogistics tripInfo = order.getTripInfo();
            BeanUtils.copyBeanProp(logistics, tripInfo);
            logistics.setTrackingNumber(order.getShipmentTrackingNo());
            logistics.setSenderTime(new Date());
            logisticsService.updateById(logistics);

            Arrays.stream(order.getJewelCode())
                    .map(Long::parseLong)
                    .forEach(id ->
                            caseService.updateJewelStatus(id, TRIP));
            order.setOrderStatus(OrderStatus.SHIPPED_AWAITING_RECEIPT);
            return orderManagementService.updateById(order);
        }
        return false;
    }
}
