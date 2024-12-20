package com.pray.entity.rbac;

import com.pray.entity.User;
import lombok.Getter;
import lombok.Setter;

/**
 * SysRoleExtend 一个人具有的角色权限结果表拓展出来的类
 *
 * @author 九歌天上有
 * @since 2024/12/17 下午8:03
 */

@Setter
@Getter
public class SysRoleExtend extends User {
    private SysRole sysRole;


    /**
     */
    @Override
    public String toString() {
        return "SysRoleExtend{" +
                "sysRole=" + sysRole +
                "} " + super.toString();
    }
}
