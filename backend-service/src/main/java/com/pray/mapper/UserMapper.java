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
    /**
     * 根据动态条件查询用户信息
     *
     * 场景：
     * 根据用户的输入条件来查询用户列表，如果输入了用户名，
     * 就根据用户名模糊查询，如果输入了邮箱，就根据邮箱精确查询，如果同时输入了用户名和邮箱，就用这两个条件去匹配用户
     * @param sysUser
     * @return
     */
    List<User> selectByUser(User sysUser);

    /**
     * 根据主键选择性更新用户信息
     *更新用户信息的时候不能将原来有值但没有发生变化的字段更新为空或null，即只更新有值的字段。
     * @param sysUser
     * @return
     */
    int updateByIdSelective(User sysUser);


    /**
     * 根据传入的参数值动态插入列
     *
     * 如果某一列的参数值不为空，就使用传入的值，如果传入的参数值为空，就使用数据库中的默认值(通常是空)，而不使用传入的空值。
     *
     * SQL添加默认字段
     *
     * ALTER TABLE sys_user
     * MODIFY COLUMN user_email VARCHAR(50) NULL DEFAULT  'test@mybatis.tk'
     * COMMENT '邮箱'
     * AFTER user_password;
     *
     * @param sysUser
     * @return
     */
    int insertSelective(User sysUser);

    /**
     * 根据用户id或用户名查询
     *
     * 使用Choose-When 语句，当条件都不满足得时候选择始终为false得条件
     * @param sysUser
     * @return
     */
    User selectByIdOrUserName(User sysUser);


    /**
     * 根据用户id获取用户信息和用户的角色信息
     *
     * @param id
     * @return
     */
    List<SysRoleExtend> selectUserAndRoleByIdResultMap(Long id);

    /**
     * 根据用户id获取用户信息和用户的角色信息，嵌套查询方式
     *
     * @param id
     * @return
     */
    List<SysRoleExtend> selectUserAndRoleByIdSelect(Long id);

}
