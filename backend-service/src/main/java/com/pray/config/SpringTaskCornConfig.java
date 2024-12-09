package com.pray.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * SpringTaskCornConfig
 *
 * @author 九歌天上有
 * @since 2024/12/9 下午8:30
 */
@Configuration
public class SpringTaskCornConfig  {
    //非必须，看自己需求
//    @Bean
//    public ThreadPoolTaskExecutor taskExecutor() {
//        ThreadPoolTaskExecutor poolTaskExecutor = new ThreadPoolTaskExecutor();
//        poolTaskExecutor.setCorePoolSize(4);
//        poolTaskExecutor.setMaxPoolSize(6);
//        // 设置线程活跃时间（秒）
//        poolTaskExecutor.setKeepAliveSeconds(120);
//        // 设置队列容量
//        poolTaskExecutor.setQueueCapacity(40);
//        poolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
//        // 等待所有任务结束后再关闭线程池
//        poolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
//        return poolTaskExecutor;
//    }

    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    public void updateRunnable(Runnable r,String corn){
        threadPoolTaskScheduler.schedule(r,new CronTrigger(corn));
    }


    public void postProcessAfterInitialization() throws BeansException {
        updateRunnable(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        },"0 * 0/2 * * ?");
    }

    /**
     * 上一次任务执行完后，歇一秒，再执行下一轮
     * 执行一次任务耗时5秒
     */
    @Scheduled(fixedDelay = 100000)
    public void task1() throws InterruptedException {

        System.out.println(Thread.currentThread().getName()
                + "==>  spring task 1 ==> "
                + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS"))
        );

        Thread.sleep(5000);
    }

    /**
     * 下轮任务在上一轮任务开始后2秒执行.
     * 执行一次任务耗时可忽略
     */
    @Scheduled(fixedRate = 200000)
    public void task2() {
        System.out.println(Thread.currentThread().getName()
                + "==>  spring task2 ==> "
                + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS"))
        );
    }
}
