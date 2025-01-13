package com.autoparams.business.loya.domain;

import com.autoparams.business.loya.server.order.Strategy.LoyaOrderUpdateGroupSequenceProvider;
import com.autoparams.business.loya.enums.OrderStatus;
import com.autoparams.common.base.annotation.Excel;
import com.autoparams.common.base.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.group.GroupSequenceProvider;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 订单管理对象 loya_order_management
 *
 * @author ruoyi
 * @date 2024-08-09
 */
@TableName(value = "loya_order_management")
@EqualsAndHashCode(callSuper = true)
@Data
@GroupSequenceProvider(value = LoyaOrderUpdateGroupSequenceProvider.class)
public class LoyaOrder extends BaseEntity {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 订单ID
     */
    @TableId(type = IdType.AUTO)
    private Long orderId;

    /**
     * 关联首饰编码
     */
    @Excel(name = "关联首饰编码")
    @NotNull(message = "关联首饰编码不能为空", groups = {Init.class})
    private String[] jewelCode;
    @TableField(exist = false)
    private List<LoyaJewel> jewels;

    /**
     * 艺人
     */
    @Excel(name = "艺人")
    @NotBlank(message = "艺人不能为空", groups = {Init.class})
    private String artistName;

    /**
     * 用途
     */
    @Excel(name = "用途")
    @NotBlank(message = "用途不能为空", groups = {Init.class})
    private String purpose;

    /**
     * 返图时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "返图时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date returnPhotoTime;

    /**
     * 露出方式
     */
    @Excel(name = "露出方式")
    private String exposureMethod;

    /**
     * 需求送达日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "需求送达日期", width = 30, dateFormat = "yyyy-MM-dd")
    @NotNull(message = "需求送达日期不能为空", groups = {Init.class})
    private Date deliveryDate;

    /**
     * 预计使用时长 (天)
     */
    @Excel(name = "预计使用时长 (天)")
    private Long expectedUsageDuration;

    /**
     * 寄出物流单号
     */
    @Excel(name = "寄出物流单号")
    @NotBlank(message = "寄出物流单号不能为空", groups = {PendingShipment.class})
    private String shipmentTrackingNo;

    /**
     * 送达日期（基础签收时间）
     */
    @Excel(name = "送达日期")
    @NotNull(message = "送达日期不能为空", groups = {Shipped.class})
    private Date arrivalDate;

    /**
     * 寄返物流单号
     */
    @Excel(name = "寄返物流单号")
    @NotBlank(message = "寄返物流单号不能为空", groups = {Received.class})
    private String[] returnShipmentTrackingNo;

    /**
     * 寄回时间以最后的时间为准
     */
    @Excel(name = "寄回时间")
    @NotNull(message = "寄回时间不能为空", groups = {Received.class})
    private Date returnDate;

    /**
     * 订单返图
     */
    @Excel(name = "订单返图")
    @NotNull(message = "订单返图不能为空", groups = {Returned.class})
    private String[] returnPhoto;

    /**
     * 宣发链接
     */
    @Excel(name = "宣发链接")
    @NotNull(message = "宣发链接不能为空", groups = {Returned.class})
    private String promotionLink;

    /**
     * 订单流程 (pending shipment, shipped awaiting receipt, received awaiting return, returned)
     */
    @Excel(name = "订单流程")
    private OrderStatus orderStatus;
    @TableField(exist = false)
    private Validator validator;
    @TableField(exist = false)
    private Set<ConstraintViolation<LoyaOrder>> violations;
    @TableField(exist = false)
    private LoyaLogistics tripInfo;
    @TableField(exist = false)
    private List<LoyaLogistics> turnInfo;

    public Boolean valid() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        violations = validator.validate(this);
        return violations.isEmpty();
    }

    // 校验组接口
    public interface Init {
    }

    public interface Locked {
    }

    public interface PendingShipment {
    }

    public interface Shipped {
    }

    public interface Received {
    }

    public interface Returned {
    }
}
