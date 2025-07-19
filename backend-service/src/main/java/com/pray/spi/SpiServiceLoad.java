package com.pray.spi;


import com.pray.LoadApplication;
import com.pray.SpringRunning;
import com.pray.facade.CheckRetryRequest;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import java.util.Map;
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
    public static void main(String[] args) throws Exception {

//        ServiceLoader<CheckRetryService> checkRetryServices = ServiceLoader.load(CheckRetryService.class);
//        for (CheckRetryService checkRetryService : checkRetryServices) {
//            CheckRetryRequest checkRetryRequest = new CheckRetryRequest();
//            checkRetryRequest.setCheck("init check");
//            RetryServiceCallable callable = new RetryServiceInvoker(checkRetryRequest,false);
//            callable.exec();
//        }
        ApplicationContext applicationContext = SpringApplication.run(SpringRunning.class, args);
        ServiceLoader checkRetryServices = (ServiceLoader) applicationContext.getBean("serviceLoader");


        CheckRetryRequest request = new CheckRetryRequest();
        request.setCheck("rainyServiceChecker");
        checkRetryServices.forEach(checkRetryService -> {
            RetryServiceCallable callable = new RetryServiceInvoker(request,true);
            callable.exec();
        });
    }
}
