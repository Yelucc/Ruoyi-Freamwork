package com.ruoyi.kuihua.domain;

import com.ruoyi.common.core.domain.model.LoginBody;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 葵花用户注册对象
 *
 * @author ruoyi
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class KhRegisterBody extends LoginBody {

    private String phone;
    private String wechatNumber;
    private String teamCode;

}
