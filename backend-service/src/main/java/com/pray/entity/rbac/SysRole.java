package com.pray.entity.rbac;


import lombok.ToString;

import java.io.Serializable;

import java.util.Date;


/**
* 角色表
* @TableName tb_sys_role
*/
public class SysRole implements Serializable {

    /**
    * 角色ID
    */

    private Long id;
    /**
    * 角色名
    */
    private String roleName;
    /**
    * 有效标志
    */
    private Integer enabled;
    /**
    * 创建人
    */
    private Long createBy;
    /**
    * 创建时间
    */
    private Date createTime;

    /**
    * 角色ID
    */
    public void setId(Long id){
    this.id = id;
    }

    /**
    * 角色名
    */
    public void setRoleName(String roleName){
    this.roleName = roleName;
    }

    /**
    * 有效标志
    */
    public void setEnabled(Integer enabled){
    this.enabled = enabled;
    }

    /**
    * 创建人
    */
    public void setCreateBy(Long createBy){
    this.createBy = createBy;
    }

    /**
    * 创建时间
    */
    public void setCreateTime(Date createTime){
    this.createTime = createTime;
    }


    /**
    * 角色ID
    */
    public Long getId(){
    return this.id;
    }

    /**
    * 角色名
    */
    public String getRoleName(){
    return this.roleName;
    }

    /**
    * 有效标志
    */
    public Integer getEnabled(){
    return this.enabled;
    }

    /**
    * 创建人
    */
    public Long getCreateBy(){
    return this.createBy;
    }

    /**
    * 创建时间
    */
    public Date getCreateTime(){
    return this.createTime;
    }

    @Override
    public String toString() {
        return "SysRole{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                ", enabled=" + enabled +
                ", createBy=" + createBy +
                ", createTime=" + createTime +
                '}';
    }
}
