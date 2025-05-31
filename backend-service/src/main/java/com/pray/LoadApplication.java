package com.pray;

import com.pray.socket.SocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * LoadApplication
 *
 * @since 2024/11/20 21:59
 */
public class LoadApplication {

    public static final Logger log = LoggerFactory.getLogger(LoadApplication.class);

    public static void socketApplication() {
        try {
            int port = 8887; // 843 flash policy port

            SocketServer s = new SocketServer(port);
            s.start();
            log.info("ChatServer started on port: {}", s.getPort());

            BufferedReader sysin = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                String in = sysin.readLine();
                s.broadcast(in);
                if (in.equals("exit")) {
                    s.stop(1000);
                    break;
                }
            }
        } catch (IOException | InterruptedException e) {
            log.error("Error in socketApplication: ", e);
        }
    }

    public static void init() throws Exception {
//        Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass("com.pray.SpringRunning");
//
//        String[] runArgs = {"--server.port=8080"};
//
//        Method method = clazz.getMethod("main", String[].class);
//        method.invoke(null, (Object) runArgs);
        SpringApplication.run(SpringRunning.class);
        socketApplication();
    }
}