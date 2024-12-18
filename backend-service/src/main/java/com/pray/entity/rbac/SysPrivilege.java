package com.pray.entity.rbac;


import java.io.Serializable;


/**
* 权限表
* @TableName tb_sys_privilege
*/
public class SysPrivilege implements Serializable {

    /**
    * 权限ID
    */
    private Long id;
    /**
    * 权限名称
    */
    private String privilegeName;
    /**
    * 权限URL
    */
    private String privilegeUrl;

    /**
    * 权限ID
    */
    public void setId(Long id){
    this.id = id;
    }

    /**
    * 权限名称
    */
    public void setPrivilegeName(String privilegeName){
    this.privilegeName = privilegeName;
    }

    /**
    * 权限URL
    */
    public void setPrivilegeUrl(String privilegeUrl){
    this.privilegeUrl = privilegeUrl;
    }


    /**
    * 权限ID
    */
    public Long getId(){
    return this.id;
    }

    /**
    * 权限名称
    */
    public String getPrivilegeName(){
    return this.privilegeName;
    }

    /**
    * 权限URL
    */
    public String getPrivilegeUrl(){
    return this.privilegeUrl;
    }

}
