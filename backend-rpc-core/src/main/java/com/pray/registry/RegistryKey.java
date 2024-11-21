package com.pray.registry;

/**
 * RegistryKey
 *
 * @author Cotton Eye Joe
 * @since 2024/11/21 15:14
 */
public interface RegistryKey {
    public static final String defaultType = "zookeeper";
    static String getRegistryKeyType() {
        return defaultType;
    }
}
