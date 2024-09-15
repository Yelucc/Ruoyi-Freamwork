package com.ruoyi.common.core.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Entity基类
 *
 * @author ruoyi
 */
@Data
public class BaseEntity implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 搜索值
     */
    @TableField(exist = false)
    @JsonIgnore
    private String searchValue;
    @TableField(fill = FieldFill.INSERT)
    /* 创建者 */
    private String createBy;
    @TableField(fill = FieldFill.INSERT)
    /* 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    /* 更新者 */
    private String updateBy;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    /* 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    @TableField(exist = false)
    /* 备注 */
    private String remark;
    @TableField(exist = false)
    /* 请求参数 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, Object> params;
}
