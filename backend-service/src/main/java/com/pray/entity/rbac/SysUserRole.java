package com.pray.entity.rbac;


import java.io.Serializable;



/**
* 用户角色关联表
* @TableName tb_sys_user_role
*/
public class SysUserRole implements Serializable {

    /**
    * 用户ID
    */
    private Long userId;
    /**
    * 角色ID
    */
    private Long roleId;

    /**
    * 用户ID
    */
    public void setUserId(Long userId){
    this.userId = userId;
    }

    /**
    * 角色ID
    */
    public void setRoleId(Long roleId){
    this.roleId = roleId;
    }


    /**
    * 用户ID
    */
    public Long getUserId(){
    return this.userId;
    }

    /**
    * 角色ID
    */
    public Long getRoleId(){
    return this.roleId;
    }

}
