package com.pray.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pray.entity.User;
import com.pray.entity.rbac.SysRole;
import com.pray.entity.rbac.SysRoleExtend;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UserMapper.xml
 *
 * @author 春江花朝秋月夜
 * @since 2024/7/13
 */
@Repository
public interface UserMapper extends BaseMapper<User> {
    List<SysRoleExtend> selectRolesByUserId(@Param("userId") int userId);
    /**
     * 根据用户id和角色的enabled状态获取用户的角色
     *   添加参数是对象的查询
     * @param user
     * @param role
     * @return
     */
    List<SysRole> selectRolesByUserAndRole(@Param("user") User user, @Param("role") SysRole role);

    void insertUseGeneratedKeys();
    void insertUseSelectKey();
}
