package com.pray.registry;

import com.pray.config.RegistryConfig;

/**
 * RegistryFactory
 *
 * @author Cotton Eye Joe
 * @since 2024/11/21 15:36
 */
public class RegistryFactory {

    /**
     * 默认的注册中心
     */
    private static final Registry DEFAULT_REGISTRY = new ZookeeperRegistry();

    public static Registry getInstance(String key){
        return null;
    }
    public static Registry getDefaultInstance(){
        return DEFAULT_REGISTRY;
    }
}
