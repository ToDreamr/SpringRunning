package com.pray.common;

import jakarta.annotation.PreDestroy;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>
 * RestrictHandler 简易版限流
 * 1.使用阻塞队列实现
 * 2.使用Map实现
 * 3.使用延时队列实现
 * 4.使用线程池实现
 * 5.使用Redis实现
 * 6.使用Lua脚本实现
 * <p>
 * @author 春江花朝秋月夜
 * @since 2023/8/25 13:13
 */
@Component
@Data
public class RestrictHandler {

    private final int limit;
    private final int time;
    private final AtomicBoolean state = new AtomicBoolean(false);
    public final BlockingQueue<TimeDelay> delayQueue=new DelayQueue<>();
    public final Map<String, DelayQueueItem> map = new ConcurrentHashMap<>();

    public void init(String key,AtomicInteger value,long time){
        map.put(key, new DelayQueueItem(value, time));
        delayQueue.add(new TimeDelay(key, time));
    }

    public boolean check(String key, int limit) {
        DelayQueueItem item = map.computeIfAbsent(key, k -> {
            DelayQueueItem newItem = new DelayQueueItem(new AtomicInteger(0), time);
            delayQueue.add(new TimeDelay(key, time));
            return newItem;
        });
        item.incr(); // 递增计数
        return item.value.get() > limit;
    }

    public void removeDelayKey(String key){
        map.remove(key);
        // 方法1：遍历队列移除（线程安全）
        delayQueue.removeIf(item -> item.key.equals(key));
    }

    @Data
    private class TimeDelay implements Delayed{
        final String key;
        final long expireTime;

        public TimeDelay(String key, long expireTime) {
            init();
            this.key = key;
            this.expireTime = new Date().getTime() + (expireTime * 1000);
        }

        private void init(){
            if (state.compareAndSet(false, true)) {
                Thread thread = new Thread(() -> {
                    while (true) {
                        try {
                            TimeDelay task = delayQueue.take();
                            map.remove(task.key);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt(); // 恢复中断状态
                            break;
                        }
                    }
                    System.out.println("清理线程已终止");  // 调试日志
                }, "RestrictHandler-Cleaner");
                thread.setDaemon(true);
                thread.start();
            }
        }

        @Override
        public int compareTo(Delayed o) {
            return Long.compare(this.getDelay(TimeUnit.MILLISECONDS),o.getDelay(TimeUnit.MILLISECONDS));
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(expireTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        @PreDestroy
        public void shutdown() {
            Thread.currentThread().interrupt();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TimeDelay timeDelay = (TimeDelay) o;
            return Objects.equals(key, timeDelay.key);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key);
        }
    }


    public static class DelayQueueItem {
        public final AtomicInteger value;
        public final long expireTime;

        DelayQueueItem(AtomicInteger value, long time) {
            this.value = value;
            this.expireTime = System.currentTimeMillis() + time * 1000;
        }

        void incr() {
            value.incrementAndGet();
        }
    }
}
