package com.pray.registry;

import com.pray.config.RegistryConfig;
import com.pray.request.ServiceMetaInfo;

import java.util.List;

/**
 * Registry
 * 规定了注册中心的行为
 * @author Cotton Eye Joe
 * @since 2024/11/21 15:16
 */
public interface Registry {
    //初始化,需要传入初始化注册中心的具体配置信息
    void init(RegistryConfig registryConfig);
    /**
     * 注册服务 服务端
     *
     * @param serviceMetaInfo
     * @throws Exception
     */
    void register(ServiceMetaInfo serviceMetaInfo) throws Exception;
    /**
     * 取消注册服务 服务端
     *
     * @param serviceMetaInfo
     */
    void unRegister(ServiceMetaInfo serviceMetaInfo);

    /**
     * 服务发现  获取某服务的所有节点  客户端 消费端
     *
     * @param serviceKey
     * @return
     */
    List<ServiceMetaInfo> serviceDiscovery(String serviceKey) throws Exception;
}
