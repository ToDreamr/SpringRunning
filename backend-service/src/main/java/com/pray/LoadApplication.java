package com.pray;

import com.pray.socket.SocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;

/**
 * LoadApplication
 *
 * @author Cotton Eye Joe
 * @since 2024/11/20 21:59
 */
public class LoadApplication {

    public static final Logger log = LoggerFactory.getLogger(LoadApplication.class);

    /**
     * @throws InterruptedException
     * @throws IOException
     */
    public static void socketApplication() throws InterruptedException, IOException {
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
    }

    public static void init() throws Exception {
//        Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass("com.pray.MethodTest");
        Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass("com.pray.SpringRunning");
        Class<?> socketApp = Thread.currentThread().getContextClassLoader().loadClass("com.pray.LoadApplication");

        String[] runArgs = {"--server.port=8080"};

        SpringRunning instance = (SpringRunning) clazz.getDeclaredConstructor().newInstance();
        Method method = clazz.getMethod("main", String[].class);
        method.invoke(null, (Object) runArgs);

        String[] socketArgs = {"--server.port=8887"};
        socketApp.getMethod("socketApplication").invoke(null);
    }
}
