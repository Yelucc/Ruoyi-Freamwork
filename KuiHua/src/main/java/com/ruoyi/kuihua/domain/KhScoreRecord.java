package com.ruoyi.kuihua.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 葵花分数记录对象 kh_score_record
 *
 * @author ruoyi
 * @date 2024-09-15
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value = "kh_score_record")
@Data
public class KhScoreRecord extends BaseEntity implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 记录ID
     */
    @TableId(type = IdType.AUTO)
    private Long recordId;

    /**
     * 用户ID
     */
//    @Excel(name = "用户ID")
    private Long userId;

    /** 用户昵称 */
    @Excel(name = "用户手机号")
    private String nickName;

    /**
     * 团队ID
     */
//    @Excel(name = "团队ID")
    private Long teamId;

    /**
     * 团队名称
     */
    @Excel(name = "团队名称")
    private String teamName;

    /**
     * 状态
     */
    @Excel(name = "状态")
    private String status;

    /**
     * 积分
     */
    @Excel(name = "积分")
    private Long score;

    /**
     * 种草链接
     */
    @Excel(name = "种草链接")
    private String sharedLink;

    /**
     * 种草图片
     */
//    @Excel(name = "种草图片")
    private String[] sharedPicture;


}
