package com.pray.netty.singlereactor;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * <p>
 * Acceptor
 * <p>
 *
 * @author 花行 (Rain)
 * @since 2025/7/13 17:06
 */
public class Acceptor implements Runnable {

    private final Logger logger = LoggerFactory.getLogger(Acceptor.class);
    private final ServerSocketChannel serverChannel;
    private final Selector selector;

    public Acceptor(ServerSocketChannel channel,Selector selector) throws IOException {
        this.serverChannel = channel;
        this.selector = selector;
    }

    /**
     *
     */
    @Override
    public void run() {
        try{
            // 建立非阻塞链接
            SocketChannel communicateChannel = serverChannel.accept();
            communicateChannel.configureBlocking(false);
            System.out.println("客户端已连接，IP地址为："+communicateChannel.getRemoteAddress());
            communicateChannel.register(selector, SelectionKey.OP_READ,new Handler(communicateChannel));

        }catch (IOException e){
            logger.error("Accept failed",e);
        }
    }
}
