package com.autoparams.business.loya.domain.vo;


import com.autoparams.business.loya.enums.OrderStatus;
import com.autoparams.common.base.utils.StringUtils;
import lombok.Data;


import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@Data
public class LoyaOrderReserveVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long orderId;

    // 首饰IDs
    private List<String> jewelCode;

    // 艺人
    private String artistName;

    // 活动内容
    private String purpose;

    // 送达时间
    private Date deliveryDate;

    // 收件人
    private String recipientName;

    // 收件电话
    private String recipientPhone;

    // 收件地址
    private String recipientAddress;

    private OrderStatus orderStatus;


    public Boolean checkPending(){
        return Stream.of(recipientAddress,recipientName,recipientPhone).allMatch(StringUtils::isNotBlank);
    }
}
