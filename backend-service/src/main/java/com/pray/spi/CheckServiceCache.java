package com.pray.spi;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 * CheckServiceCache
 * <p>
 *
 * @author 花行 (Rain)
 * @since 2025/7/19 15:43
 */
public class CheckServiceCache {

    /**
     * 业务重试服务缓存
     */
    private static Map<String,CheckRetryService> checkRetryServiceMap = new ConcurrentHashMap<>();

    /**
     * 获取重试服务
     * @param uniqueId
     * @return
     */
    public static CheckRetryService getUniqueById(String uniqueId) {
        return checkRetryServiceMap.get(uniqueId);
    }

    /**
     * 注册重试服务
     * @param serviceMap
     */
    public static void registryRetryService(Map<String,CheckRetryService> serviceMap) {
        checkRetryServiceMap.putAll(serviceMap);
    }


}
