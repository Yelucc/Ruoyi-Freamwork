package com.ruoyi.kuihua.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;
import com.ruoyi.common.core.domain.BaseEntity;
import java.io.Serializable;
/**
 * 用户管理对象 kh_user
 *
 * @author ruoyi
 * @date 2024-09-15
 */
@TableName(value ="kh_user")
@Data
public class KhUser implements Serializable
{
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /** 用户ID */
    @TableId(type = IdType.AUTO)
    private Long userId;

    /** 系统用户ID */
    @Excel(name = "系统用户ID")
    private Long sysUserId;

    /** 所属团队ID */
    @Excel(name = "所属团队ID")
    private Long teamId;

    /** 微信账号 */
    @Excel(name = "微信账号")
    private String wechatNumber;

    /** 手机号 */
    @Excel(name = "手机号")
    private String phone;

    /** 用户昵称 */
    @Excel(name = "用户昵称")
    private String nickName;



}
