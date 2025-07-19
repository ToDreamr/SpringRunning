package com.pray.spi;


import com.pray.facade.CheckRetryRequest;
import com.pray.facade.CheckRetryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * RetryServiceInvoker
 * <p>
 *
 * @author 花行 (Rain)
 * @since 2025/7/19 16:38
 */
public class RetryServiceInvoker implements RetryServiceCallable{

    private static final Logger logger = LoggerFactory.getLogger(RetryServiceInvoker.class);

    private final CheckRetryRequest checkRetryRequest;

    private final boolean sync;

    public RetryServiceInvoker(CheckRetryRequest checkRetryRequest, boolean sync) {
        this.checkRetryRequest = checkRetryRequest;
        this.sync = sync;
    }

    /**
     * 执行重试任务
     *
     * @return
     */
    @Override
    public Object exec() {
        try {
            // 获取重试服务
            CheckRetryService checkRetryService = CheckServiceCache.getUniqueById(checkRetryRequest.getCheck()+"_retry");
            if (checkRetryService == null) {
                logger.error("未找到重试服务");
                return new CheckRetryResult(false,"无可用的重试服务");
            }
            if (sync) {
                return checkRetryService.syncRetryExecute(checkRetryRequest);
            }
            return checkRetryService.asyncRetryExecute(checkRetryRequest);
        }catch (Throwable e) {
            logger.error("重试服务执行异常",e);
            return new CheckRetryResult(false,e.getMessage());
        }
    }
}
