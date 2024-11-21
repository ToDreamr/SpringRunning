package com.pray.config;

import com.pray.registry.RegistryKey;
import lombok.Getter;
import lombok.Setter;

/**
 * RegistryConfig
 *
 * @author Cotton Eye Joe
 * @since 2024/11/21 15:19
 */
@Setter
@Getter
public class RegistryConfig {
    private String registry = RegistryKey.getRegistryKeyType();
    private String address = "127.0.0.1:2181";

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 超时时间 ms
     */
    private Long timeout = 10000L;
}
