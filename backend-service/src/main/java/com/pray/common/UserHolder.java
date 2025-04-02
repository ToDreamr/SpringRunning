package com.pray.common;

import com.pray.entity.dto.UserDto;

/**
 * <p>
 * UserHolder 用户信息线程存储
 * <p>
 *
 * @author 春江花朝秋月夜
 * @since 2023/8/24
 */
public class UserHolder {
    private static final ThreadLocal<UserDto> currentUser =new ThreadLocal<>();

    public static void setLocalUser(UserDto userDto) {
          currentUser.set(userDto);
    }

    public static UserDto getLocalUser() {
        return currentUser.get();
    }

    public void removeLocalUser(){
        currentUser.remove();
    }
}
