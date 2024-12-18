package com.pray.entity.rbac;

import com.pray.entity.User;

/**
 * SysRoleExtend 一个人具有的角色权限结果表拓展出来的类
 *
 * @author 九歌天上有
 * @since 2024/12/17 下午8:03
 */

public class SysRoleExtend extends SysRole {
    private User sysUser;

    public User getSysUser() {
        return sysUser;
    }

    public void setSysUser(User sysUser) {
        this.sysUser = sysUser;
    }

    /**
     * @return
     */
    @Override
    public String toString() {
        return "SysRoleExtend{" +
                "sysUser=" + sysUser +
                "} " + super.toString();
    }
}
