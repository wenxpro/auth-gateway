package com.wenx.domain.modular.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wenx.domain.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * @author wenx
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user")
@Accessors(chain = true)
public class SysUser extends BaseEntity {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /** 用户名称*/
    private String name;

    /** 账户*/
    private String account;

    /** 密码*/
    private String password;

    /** 状态*/
    private Integer status;

    /** 删除编辑*/
    private Integer isDeleted;

}
