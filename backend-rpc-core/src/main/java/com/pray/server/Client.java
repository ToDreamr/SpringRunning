package com.pray.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;

/**
 * Client
 *
 * @author Cotton Eye Joe
 * @since 2024/11/22 0:23
 */
public class Client {
    public static void main(String[] args) throws IOException {

            ServerSocketChannel channel = ServerSocketChannel.open();
            channel.bind(new InetSocketAddress("127.0.0.1",8081));
            System.out.println(channel.accept().getRemoteAddress());

    }
}

