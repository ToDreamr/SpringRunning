package com.pray;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * LoadApplication
 *
 * @author Cotton Eye Joe
 * @since 2024/11/20 21:59
 */
public class LoadApplication {
    public static void init() throws Exception {
//        Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass("com.pray.MethodTest");
        Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass("com.pray.SpringRunning");

        String[] runArgs = {"--server.port=8080"};

        SpringRunning instance = (SpringRunning) clazz.getDeclaredConstructor().newInstance();
        Method method = clazz.getMethod("main", String[].class);
        method.invoke(null, (Object) runArgs);
    }
}
