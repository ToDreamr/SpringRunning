package com.pray.provider;

import com.pray.config.RpcConfig;
import com.pray.server.NettyServer;
import lombok.extern.slf4j.Slf4j;


/**
 * SimpleNettyProvider
 *
 * @author Cotton Eye Joe
 * @since 2024/11/21 18:03
 */
@Slf4j
public class SimpleNettyProvider {
    public static void main(String[] args) {
        RpcApplication.init();
        RpcConfig config=RpcApplication.getRpcConfig();
        NettyServer nettyServer = new NettyServer();
        nettyServer.start(config.getServerPort());
        log.info("服务已开启，端口为{}",config.getServerPort());
        nettyServer.start(config.getServerPort());
    }
}
