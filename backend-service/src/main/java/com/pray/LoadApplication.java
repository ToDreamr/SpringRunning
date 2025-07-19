package com.pray;

import com.pray.netty.singlereactor.Reactor;
import com.pray.netty.socket.SocketServer;
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

            Reactor reactor = new Reactor(port);
            reactor.run();
        } catch (IOException e) {
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