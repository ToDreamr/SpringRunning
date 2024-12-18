package com.pray.rbac.mapper;

import com.pray.entity.rbac.SysRolePrivilege;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Rainy-Heights
* @description 针对表【tb_sys_role_privilege(角色权限关联表)】的数据库操作Mapper
* @createDate 2024-12-17 19:40:57
* @Entity com.pray.entity.rbac.SysRolePrivilege
*/
@Mapper
public interface SysRolePrivilegeMapper extends BaseMapper<SysRolePrivilege> {

}




