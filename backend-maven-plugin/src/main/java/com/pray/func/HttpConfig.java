package com.pray.func;

import java.util.Arrays;

/**
 * HttpConfig
 *
 * @author Cotton Eye Joe
 * @since 2024/11/20 23:35
 */
public class HttpConfig {
    public static void fun(){
        String[] asStrings = Arrays.asList("1", "2", "3").toArray(new String[0]);
        GeneralConfig addConfig = GeneralConfig.getInstance().addConfig(System.out::println, "dev")
                .addConfig(System.out::println,2).addConfig(System.out::println,asStrings);
    }

    public static void main(String[] args) {
        fun();
    }
}
