package com.autoparams.business.loya.enums;


import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum Category {
    EARRINGS("earrings", "耳饰"),
    NECKLACE("necklace", "项链"),
    BRACELET("bracelet", "手链"),
    BANGLE("bangle", "手镯"),
    BROOCH("brooch", "胸针"),
    RING("ring", "戒指");

    @EnumValue
    @JsonValue
    private final String code;

    private final String description;

    Category(String code, String description) {
        this.code = code;
        this.description = description;
    }

    @JsonCreator
    public static Category fromCode(String code) {
        for (Category category : Category.values()) {
            if (category.code.equalsIgnoreCase(code)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Unknown category code: " + code);
    }
}
