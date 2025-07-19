package com.pray.spi;


import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;
import org.springframework.core.metrics.ApplicationStartup;
import org.springframework.core.metrics.StartupStep;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * CheckRetryServiceCache
 * <p>
 *
 * @author 花行 (Rain)
 * @since 2025/7/19 15:52
 */
@Component
public class CheckRetryServiceCache implements ApplicationStartup {

    private ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();

    /**
     * 重试服务的缓存
     * @param cacheRetry
     */
    private static Set<String> retryServiceChecker = Sets.newConcurrentHashSet();


    /**
     * @param name
     * @return
     */
    @Override
    public StartupStep start(String name) {
        scheduledExecutor.scheduleWithFixedDelay(
                new Runnable() {
                    @Override
                    public void run() {
                        loadRetryServiceChecker();
                        refreshSubscriberChecker();
                    }
                }
        ,0,180, TimeUnit.SECONDS);
        return null;
    }

    private void loadRetryServiceChecker() {
        retryServiceChecker.add("rainyServiceChecker");
        retryServiceChecker.add("saleForceServiceChecker");
        retryServiceChecker.add("iexpbizfundprodServiceChecker");
    }

    private void refreshSubscriberChecker() {
        if (CollectionUtils.isEmpty(retryServiceChecker)) {
            return;
        }
        Map<String,CheckRetryService> checkRetryServiceMap = Maps.newConcurrentMap();
        for (String uniqueId : retryServiceChecker) {
            checkRetryServiceMap.put(uniqueId,new DefaultCheckRetryServiceImpl());
        }
        CheckServiceCache.registryRetryService(checkRetryServiceMap);
    }


}
