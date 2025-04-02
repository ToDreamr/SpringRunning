package com.pray.cache;

import cn.hutool.core.util.BooleanUtil;
import com.pray.constant.PrayConstants;
import com.pray.entity.dto.RedisData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * <p>
 * PrayCacheClient 基于RedisTemplate封装一套缓存工具类，解决缓存穿透，缓存雪崩，缓存击穿
 * <p>
 *
 * @author 春江花朝秋月夜
 * @since 2023/9/14 17:14
 */
@Slf4j
@Component
public class PrayCacheClient {
    private static final ExecutorService CACHE_REBUILD_EXECUTOR = Executors.newFixedThreadPool(10);

    @Autowired
    RedisTemplate<Object,Object> redisTemplate;

    public void set(String key, Object value, Long time, TimeUnit unit){
        redisTemplate.opsForValue().set(key,value,time,unit);
    }

    /**
     * 存储数据同时设置逻辑过期时间
     * @param key
     * @param value
     * @param time
     * @param unit
     */
    public void setWithExpire(String key, Object value, Long time, TimeUnit unit){
        RedisData redisData = new RedisData();
        redisData.setData(value);
        //转换传入时间为秒，设置过期时间
        redisData.setExpireTime(LocalDateTime.now().plusSeconds(unit.toSeconds(time)));
        redisTemplate.opsForValue().set(key,redisData);
    }

    private boolean tryLock(String key) {
        //调用setIfNx方法
        Boolean flag = redisTemplate.opsForValue().setIfAbsent(key, "1", 10, TimeUnit.SECONDS);
        return BooleanUtil.isTrue(flag);
    }
    private void unLock(String key){
        redisTemplate.delete(key);
    }
}
