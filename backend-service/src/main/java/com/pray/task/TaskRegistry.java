package com.pray.task;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

/**
 * TaskRegistry
 *
 * @author 九歌天上有
 * @since 2024/12/16 下午10:26
 */
@Component
public class TaskRegistry {
    private final ConcurrentHashMap<String, ScheduledFuture<?>> taskFutures = new ConcurrentHashMap<>();

    /**
     * 注册定时任务
     * @param taskName
     * @param future
     */
    public void scheduledTaskRegistry(String taskName, List<ScheduledFuture<?>> future) {
        future.forEach(futureTask -> taskFutures.put(taskName, futureTask));
    }

    /**
     * 删除定时任务
     * @param taskName
     */
    public void removeScheduledTask(String taskName) {
        ScheduledFuture<?> future = taskFutures.get(taskName);
        if (future != null) {
            future.cancel(true);
            taskFutures.remove(taskName);
        }
    }
}