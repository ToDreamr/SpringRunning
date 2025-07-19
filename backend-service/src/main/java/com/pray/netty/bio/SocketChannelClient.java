package com.pray.netty.bio;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * <p>
 * SocketChannelClient
 * <p>
 *
 * @author 花行 (Rain)
 * @since 2025/7/13 14:03
 */
public class SocketChannelClient {
    private static final Logger logger = LoggerFactory.getLogger("SocketChannelServer");

    public static void main(String[] args) throws IOException {
        SocketChannel clientChannel = null;
        try {
            clientChannel = SocketChannel.open();
            clientChannel.connect(new InetSocketAddress("localhost", 8080));

            Scanner scanner = new Scanner(System.in);

            while (scanner.hasNextLine()) {
                clientChannel.write(ByteBuffer.wrap(scanner.nextLine().getBytes()));
            }

        } catch (Exception e) {
            logger.error("Error opening server socket channel", e);
        } finally {
            assert clientChannel != null;
            clientChannel.close();
        }
    }
}
