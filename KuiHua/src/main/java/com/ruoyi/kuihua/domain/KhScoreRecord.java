package com.ruoyi.kuihua.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.io.Serializable;

/**
 * 葵花分数记录对象 kh_score_record
 *
 * @author ruoyi
 * @date 2024-09-15
 */
@TableName(value ="kh_score_record")
@Data
public class KhScoreRecord implements Serializable
{
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /** 记录ID */
    @TableId(type = IdType.AUTO)
    private Long recordId;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 积分 */
    @Excel(name = "积分")
    private Long score;

    /** 种草链接 */
    private String sharedLink;

    /** 种草图片 */
    private String sharedPicture;



}
