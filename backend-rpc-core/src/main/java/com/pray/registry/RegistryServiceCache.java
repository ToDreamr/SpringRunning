package com.pray.registry;

import com.pray.request.ServiceMetaInfo;

import java.util.List;

/**
 * RegistryServiceCache
 *
 * @author Cotton Eye Joe
 * @since 2024/11/21 15:51
 */
public class RegistryServiceCache {
    /**
     * 服务缓存
     */
    List<ServiceMetaInfo> serviceCache;

    /**
     * 写缓存
     */
    void writeCache(List<ServiceMetaInfo> newServiceCache) {
        serviceCache = newServiceCache;
    }

    /**
     * 读缓存
     */
    List<ServiceMetaInfo> readCache() {
        return serviceCache;
    }

    /**
     * 清空缓存
     */
    void clearCache() {
        serviceCache = null;
    }

}
