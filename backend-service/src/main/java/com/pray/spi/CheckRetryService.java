package com.pray.spi;


import com.pray.facade.CheckRetryRequest;
import com.pray.facade.CheckRetryResult;

/**
 * <p>
 * CheckRetryService
 * <p>
 *
 * @author 花行 (Rain)
 * @since 2025/7/19 15:40
 */
public interface CheckRetryService {
    /**
     * 异步重试执行
     *
     * @param checkRetryRequest
     * @return CheckRetryResult
     */
    CheckRetryResult asyncRetryExecute(CheckRetryRequest checkRetryRequest);

    /**
     * 同步重试执行
     *
     * @param checkRetryRequest
     * @return CheckRetryResult
     *
     */
    CheckRetryResult syncRetryExecute(CheckRetryRequest checkRetryRequest);
}
