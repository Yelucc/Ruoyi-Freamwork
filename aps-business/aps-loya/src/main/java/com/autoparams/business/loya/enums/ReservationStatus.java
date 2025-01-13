package com.autoparams.business.loya.enums;

import com.autoparams.business.loya.domain.LoyaJewel;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum ReservationStatus {
    AVAILABLE("available", "空闲"),
    RESERVED("reserved", "已预定"),
    TRIP("trip", "出差中");

    @EnumValue
    @JsonValue
    private final String code;

    private final String description;

    ReservationStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

    @JsonCreator
    public static ReservationStatus fromCode(String code) {
        for (ReservationStatus item : ReservationStatus.values()) {
            if (item.code.equalsIgnoreCase(code)) {
                return item;
            }
        }
        throw new IllegalArgumentException("Unknown category code: " + code);
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
