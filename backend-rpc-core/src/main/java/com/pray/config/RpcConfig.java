package com.pray.config;

import com.pray.serializer.SerializeKeys;
import lombok.Getter;
import lombok.Setter;

/**
 * RpcConfig
 *
 * @author Cotton Eye Joe
 * @since 2024/11/21 15:30
 */
@Setter
@Getter
public class RpcConfig {
    /**
     * 名称
     */
    private String name = "todreamr-rpc";

    /**
     * 版本号
     */
    private String version = "1.0";

    /**
     * 服务器主机
     */
    private String serverHost = "localhost";

    /**
     * 服务器端口
     */
    private int serverPort = 8080;
    /**
     * 序列化器
     */
    private String serializer = SerializeKeys.JDK;

    //服务器配置好了，现在需要定义下注册中心的配置
    private RegistryConfig registryConfig = new RegistryConfig();

}
