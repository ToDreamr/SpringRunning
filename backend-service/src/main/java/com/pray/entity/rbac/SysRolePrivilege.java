package com.pray.entity.rbac;


import java.io.Serializable;



/**
* 角色权限关联表
* @TableName tb_sys_role_privilege
*/
public class SysRolePrivilege implements Serializable {

    /**
    * 角色ID
    */
    private Long roleId;
    /**
    * 权限ID
    */
    private Long privilegeId;

    /**
    * 角色ID
    */
    public void setRoleId(Long roleId){
    this.roleId = roleId;
    }

    /**
    * 权限ID
    */
    public void setPrivilegeId(Long privilegeId){
    this.privilegeId = privilegeId;
    }


    /**
    * 角色ID
    */
    public Long getRoleId(){
    return this.roleId;
    }

    /**
    * 权限ID
    */
    public Long getPrivilegeId(){
    return this.privilegeId;
    }

}
