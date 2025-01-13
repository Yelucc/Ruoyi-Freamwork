package com.autoparams.business.loya.domain;

import cn.hutool.core.map.MapUtil;
import com.autoparams.common.base.annotation.Excel;
import com.autoparams.common.base.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 物流管理对象 loya_logistics
 *
 * @author Yelucc
 * @date 2024-08-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "loya_logistics")
public class LoyaLogistics extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long logisticsId;
    /**
     * 物流单号
     */

    private String trackingNumber;

    /**
     * 关联订单ID
     */
    @Excel(name = "关联订单ID")
    private Long orderId;

    private String confirmStatus;
    private String exceptDesc;
    /**
     * 关联多个首饰ID，用逗号分隔
     */
    @Excel(name = "关联首饰ID")
    private String[] jewelIds;
    private String[] exceptJewelIds;

    public List<String> getPassJewelIds() {
        return Arrays.stream(jewelIds).filter(id-> Arrays.stream(exceptJewelIds).noneMatch(ex->ex.equals(id))).collect(Collectors.toList());
    }

    @TableField(exist = false)
    private String[] passJewelIds;
    /**
     * 物流方向 trip turn
     */
    private String direction;
    /**
     * 物流公司
     */
    private String shipper;

    /**
     * 寄件人
     */
    @Excel(name = "寄件人")
    private String senderName;

    /**
     * 收件人
     */
    @Excel(name = "收件人")
    private String recipientName;

    /**
     * 寄件电话
     */
    @Excel(name = "寄件电话")
    private String senderPhone;

    /**
     * 收件电话
     */
    @Excel(name = "收件电话")
    private String recipientPhone;

    /**
     * 寄件地址
     */
    @Excel(name = "寄件地址")
    private String senderAddress;

    /**
     * 收件地址
     */
    @Excel(name = "收件地址")
    private String recipientAddress;

    @Excel(name = "寄件时间")
    private Date senderTime;

    /**
     * 收件时间
     */
    @Excel(name = "收件时间")
    private Date recipientTime;


    public Map<String, String> vantAddress() {
        return MapUtil.builder(new HashMap<String, String>())
                .put("id", logisticsId.toString())
                .put("address", recipientAddress)
                .put("tel", recipientPhone)
                .put("name", recipientName).build();
    }
}
