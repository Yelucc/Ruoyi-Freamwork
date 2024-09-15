package com.ruoyi.system.domain;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 短链映射对象 sys_url_map
 *
 * @author ruoyi
 * @date 2024-09-15
 */
@TableName(value ="sys_url_map")
@Data
public class SysUrlMap implements Serializable
{
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /** 短链 */
    @TableId(type = IdType.INPUT)
    private String shortUrl;

    /** 原始链 */
    private String longUrl;

    /** 过期时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date expiresTime;
    /* 创建时间 */

    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}
