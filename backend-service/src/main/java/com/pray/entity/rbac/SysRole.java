package com.pray.entity.rbac;


import com.pray.rbac.type.RoleEnabled;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import java.util.Date;


/**
* 角色表
* &#064;TableName  tb_sys_role
 */
@Setter
@Getter
public class SysRole implements Serializable {

    /**
    * 角色ID
     * -- GETTER --
     *  角色ID
     * -- SETTER --
     *  角色ID


     */

    private Long id;
    /**
    * 角色名
     * -- GETTER --
     *  角色名
     * -- SETTER --
     *  角色名


     */
    private String roleName;
    /**
    * 有效标志
     * -- GETTER --
     *  有效标志
     * -- SETTER --
     *  有效标志
     * 替换为枚举字段
     * 数据库并不能识别Enabled枚举类型，在新增，更新或者作为查询条件时，
     * 需要将枚举值转换为数据库中的int类型，在查询数据时，需要将数据库的int类型的值转换为Enabled枚举类型。
     * MyBatis还提供了另一个枚举处理器：org.apache.ibatis.type.EnumOrdinalTypeHandler，
     * 这个处理器使用枚举的索引进行处理，可以解决此处转换报错的问题
     */
    private RoleEnabled enabled;
    /**
    * 创建人
     * -- GETTER --
     *  创建人
     * -- SETTER --
     *  创建人


     */
    private Long createBy;
    /**
    * 创建时间
     * -- GETTER --
     *  创建时间
     * -- SETTER --
     *  创建时间


     */
    private Date createTime;


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
