package com.pray.rbac.mapper;

import com.pray.entity.rbac.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pray.rbac.provider.SysRoleSQLProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
* @author Rainy-Heights
* @description 针对表【tb_sys_role(角色表)】的数据库操作Mapper
* @createDate 2024-12-17 19:40:57
* @Entity com.pray.entity.rbac.SysRole
*/
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 类似于xml中的resultMap
     * @param id
     * @return
     */
    @Results({
            @Result(property = "id", column = "id", id = true),
            @Result(property = "roleName", column = "role_name"),
            @Result(property = "enabled", column = "enabled"),
            @Result(property = "createBy", column = "create_by"),
            @Result(property = "createTime", column = "create_time")
    })
    @Select("SELECT id,role_name,enabled,create_by,create_time FROM tb_sys_role WHERE id = #{id}")
    SysRole selectByIds(Long id);

    //    @SelectProvider(type = SysRoleSQLProvider.class, method = "selectByProvidedSQL")
    //    SysRole selectBySQLProvider(Long id);
    List<SysRole> selectRoleById(int id);

}




