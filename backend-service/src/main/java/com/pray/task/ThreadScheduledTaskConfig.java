package com.pray.task;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

/**
 * ThreadScheduledTaskConfig
 *
 * @author 九歌天上有
 * @since 2024/12/9 下午8:57
 */
@Configuration
public class ThreadScheduledTaskConfig {

        // 用来存入线程执行情况, 方便于停止定时任务时使用
        public static ConcurrentHashMap<String, ScheduledFuture> cache= new ConcurrentHashMap<String, ScheduledFuture>();

        //@Schduled注解使用的线程池
        @Bean
        public ThreadPoolTaskScheduler threadPoolTaskScheduler(){
            ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
            threadPoolTaskScheduler.setPoolSize(2);                        // 线程池大小
            threadPoolTaskScheduler.setThreadNamePrefix("taskExecutor-");   // 线程名称
            threadPoolTaskScheduler.setAwaitTerminationSeconds(60);         // 等待时长
            threadPoolTaskScheduler.setWaitForTasksToCompleteOnShutdown(true);  // 调度器shutdown被调用时等待当前被调度的任务完成
            return threadPoolTaskScheduler;
        }
}
