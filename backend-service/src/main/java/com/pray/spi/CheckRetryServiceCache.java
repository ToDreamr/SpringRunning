package com.pray.spi;


import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.InitializingBean;
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
public class CheckRetryServiceCache implements InitializingBean {

    private ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();

    /**
     * 重试服务的缓存
     * @param cacheRetry
     */
    private static Set<String> retryServiceChecker = Sets.newConcurrentHashSet();

    private void loadRetryServiceChecker() {
        retryServiceChecker.add("rainyServiceChecker_retry");
        retryServiceChecker.add("saleForceServiceChecker_retry");
        retryServiceChecker.add("iexpbizfundprodServiceChecker_retry");
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


    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        scheduledExecutor.scheduleWithFixedDelay(
                new Runnable() {
                    @Override
                    public void run() {
                        loadRetryServiceChecker();
                        refreshSubscriberChecker();
                    }
                }
                ,0,180, TimeUnit.SECONDS);
    }
}
