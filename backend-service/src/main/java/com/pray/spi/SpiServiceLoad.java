package com.pray.spi;


import com.pray.facade.CheckRetryRequest;

import java.util.ServiceLoader;

/**
 * <p>
 * SpiServiceLoad
 * <p>
 *
 * @author 花行 (Rain)
 * @since 2025/7/19 19:14
 */
public class SpiServiceLoad {
    public static void main(String[] args) {
        ServiceLoader<CheckRetryService> checkRetryServices = ServiceLoader.load(CheckRetryService.class);
        for (CheckRetryService checkRetryService : checkRetryServices) {
            CheckRetryRequest checkRetryRequest = new CheckRetryRequest();
            checkRetryRequest.setCheck("init check");
            checkRetryService.asyncRetryExecute(checkRetryRequest);
        }
    }
}
