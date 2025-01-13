package com.autoparams.business.loya.domain.vo;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;

import com.autoparams.business.loya.domain.LoyaJewel;
import com.autoparams.business.loya.domain.LoyaOrder;
import com.autoparams.business.loya.enums.OrderStatus;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class LoyaOrderVo implements Serializable {
    private static final long serialVersionUID = 1L;


    private Long orderId;
    private String[] jewelCode;
    private List<LoyaJewel> jewels;
    private String artistName;
    private String purpose;
    private Date deliveryDate;

    private OrderStatus orderStatus;

    private String statusDescription;
    private LoyaOrder order;


    public static LoyaOrderVo of(LoyaOrder order) {
        LoyaOrderVo vo = new LoyaOrderVo();
        vo.setOrderId(order.getOrderId());
        vo.setJewelCode(order.getJewelCode());
        vo.setArtistName(order.getArtistName());
        vo.setPurpose(order.getPurpose());
        vo.setOrder(order);
        vo.setJewels(order.getJewels());
        vo.setDeliveryDate(order.getDeliveryDate());
        vo.setOrderStatus(order.getOrderStatus());
        vo.setStatusDescription(String.format(order.getOrderStatus().getDescription(), 7 - DateUtil.between(order.getCreateTime(), new Date(), DateUnit.DAY)));
        return vo;
    }
}
