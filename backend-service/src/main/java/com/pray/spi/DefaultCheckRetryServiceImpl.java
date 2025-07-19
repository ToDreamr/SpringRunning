package com.pray.spi;


import com.pray.facade.CheckRetryRequest;
import com.pray.facade.CheckRetryResult;

/**
 * <p>
 * DefaultCheckRetryServiceImpl
 * <p>
 *
 * @author 花行 (Rain)
 * @since 2025/7/19 16:32
 */
public class DefaultCheckRetryServiceImpl implements CheckRetryService {
    /**
     * @param checkRetryRequest
     * @return
     */
    @Override
    public CheckRetryResult asyncRetryExecute(CheckRetryRequest checkRetryRequest) {
        System.out.println(checkRetryRequest);
        return null;
    }

    /**
     * @param checkRetryRequest
     * @return
     */
    @Override
    public CheckRetryResult syncRetryExecute(CheckRetryRequest checkRetryRequest) {
        return null;
    }


}
