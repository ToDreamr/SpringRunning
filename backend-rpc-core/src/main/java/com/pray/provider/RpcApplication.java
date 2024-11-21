package com.pray.provider;

import com.pray.config.RegistryConfig;
import com.pray.config.RpcConfig;
import com.pray.registry.Registry;
import com.pray.registry.RegistryFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * RpcApplication
 *
 * @author Cotton Eye Joe
 * @since 2024/11/21 18:03
 */
@Slf4j
public class RpcApplication {
    private static volatile RpcConfig rpcConfig;
    public static void init(){
        RpcConfig config;
        //TODO 使用配置文件写入加载配置
        config=new RpcConfig();
        init(config);
    }
    public static void init(RpcConfig newRpcConfig) {
        rpcConfig = newRpcConfig;
        log.info("rpc application init success,config:{}", rpcConfig);
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        //进行注册中心的初始化
        Registry registry = RegistryFactory.getDefaultInstance();
        registry.init(registryConfig);
        log.info("registry init success,config:{}", registryConfig);
    }
    /**
     * 获取配置,和之前想过的单例模式思想差不多
     */
    public static RpcConfig getRpcConfig() {
        if (rpcConfig == null) {
            synchronized (RpcApplication.class) {
                if (rpcConfig == null) {
                    init();
                }

            }
        }
        return rpcConfig;
    }
}
