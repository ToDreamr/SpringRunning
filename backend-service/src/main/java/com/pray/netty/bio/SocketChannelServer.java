package com.pray.netty.bio;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;


/**
 * <p>
 * SocketChannelServer
 * <p>
 *
 * @author 花行 (Rain)
 * @since 2025/7/13 13:50
 */
public class SocketChannelServer {

    private static final Logger logger = LoggerFactory.getLogger("SocketChannelServer");

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = null;

        try {

            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(8888));

            while (true){
                //不断地阻塞等待链接
                SocketChannel establishedChannel = serverSocketChannel.accept();

                logger.info("Socket channel accepted, IP address: {}", serverSocketChannel.socket().getInetAddress());

                new Thread(() -> {
                    //读写数据
                    ByteBuffer allocate = ByteBuffer.allocate(1024);
                    try {
                        long read = establishedChannel.read(allocate);
                        allocate.flip();
                        logger.info("Read {}", new String(allocate.array(),0,allocate.remaining()));

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }).start();

            }

        } catch (Exception e) {
            logger.error("Error opening server socket channel", e);
        } finally {
            assert serverSocketChannel != null;
            serverSocketChannel.close();
        }
    }
}
