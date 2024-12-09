package com.pray;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * @author Rainy-Heights
 */
@EnableScheduling
@EnableWebSecurity
@SpringBootApplication(
//        exclude = {
//                RedisAutoConfiguration.class,
//                RedisRepositoriesAutoConfiguration.class
//        }
)
@MapperScan(basePackages = "com.pray.mapper")
@Slf4j
public class SpringRunning {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(SpringRunning.class, args);
        Environment environment= applicationContext.getEnvironment();
        log.info("当前运行环境：Java版本：{}",environment.getProperty("java.version"));
    }
}

