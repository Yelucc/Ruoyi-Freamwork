package com.autoparams.business.loya.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum OrderStatus {
    INIT("init", "初始化"),
    CLOSE("close", "订单已关闭"),
    LOCKED("locked", "订单还有%s天超时，请及时补充地址信息"),
    PENDING_SHIPMENT("pending_shipment", "待寄出"),             // 订单已创建，待寄出首饰
    SHIPPED_AWAITING_RECEIPT("shipped_awaiting_receipt", "已寄出待签收"), // 首饰已寄出，等待客户签收
    RECEIVED_AWAITING_RETURN("received_awaiting_return", "已签收待寄回"), // 首饰已签收，等待寄回
    RETURN_AWAITING_CHECK("return_awaiting_check", "已寄回待审核"),
    RETURNED("returned", "待反馈");                     // 首饰已寄回，订单完成

    private final String description;
    @JsonValue
    @EnumValue
    private final String value;

    OrderStatus(String value, String description) {
        this.value = value;
        this.description = description;
    }

    @JsonCreator
    public static OrderStatus fromCode(String code) {
        for (OrderStatus item : OrderStatus.values()) {
            if (item.value.equalsIgnoreCase(code)) {
                return item;
            }
        }
        throw new IllegalArgumentException("Unknown category code: " + code);
    }
}
