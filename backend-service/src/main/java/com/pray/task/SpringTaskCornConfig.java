package com.pray.task;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * SpringTaskCornConfig
 *
 * @author 九歌天上有
 * @since 2024/12/9 下午8:30
 */
@Configuration
public class SpringTaskCornConfig  implements ApplicationRunner {
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
    @Autowired
    private TaskRegistry taskRegistry;

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
    public void task2() {
        System.out.println(Thread.currentThread().getName()
                + "==>  spring task2 ==> "
                + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS"))
        );
    }
    public ScheduledFuture<?> addScheduledTasks() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        ScheduledFuture<?> task1 = executor.scheduleAtFixedRate(() -> {
            try {
                task1();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, 0, 5000, TimeUnit.MILLISECONDS);
        ScheduledFuture<?> task2 = executor.scheduleAtFixedRate(this::task2, 0, 10000, TimeUnit.MILLISECONDS);
        taskRegistry.scheduledTaskRegistry("task1", List.of(task1,task2));
        return task1;
    }
    public void removeTask() {
        taskRegistry.removeScheduledTask("task1");
        taskRegistry.removeScheduledTask("task2");
    }

    /**
     * @param args incoming application arguments
     */
    @Override
    public void run(ApplicationArguments args) {
      //  ScheduledFuture<?> future = addScheduledTasks();
    }
}
