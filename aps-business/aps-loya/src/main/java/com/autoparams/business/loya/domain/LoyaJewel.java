package com.autoparams.business.loya.domain;

import com.autoparams.business.loya.enums.Category;
import com.autoparams.business.loya.enums.ReservationStatus;
import com.autoparams.common.base.annotation.Excel;
import com.autoparams.common.base.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Date;

/**
 * 首饰管理对象 loya_jewel_case
 *
 * @author Yeeluc
 * @date 2024-08-09
 */
@TableName(value = "loya_jewel_case")
@EqualsAndHashCode(callSuper = true)
@Data
public class LoyaJewel extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 首饰ID
     */
    @TableId(type = IdType.AUTO)
    private Long jewelId;
    private String jewelName;
    /**
     * 首饰分类 (earrings, necklace, bracelet, bangle, brooch, ring)
     */
    @Excel(name = "首饰分类")
    private Category category;

    /**
     * 首饰图片地址
     */
    @Excel(name = "首饰图片地址")
    private String imageUrl;

    /**
     * 首饰预定状态 (available, reserved, trip)
     */
    @Excel(name = "首饰预定状态")
    private ReservationStatus reservationStatus;

    /**
     * 预计寄回时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "预计寄回时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date expectedReturnTime;

}
