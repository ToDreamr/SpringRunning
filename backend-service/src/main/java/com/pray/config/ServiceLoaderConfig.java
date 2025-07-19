package com.pray.config;


import com.pray.spi.CheckRetryService;
import org.springframework.beans.factory.serviceloader.ServiceLoaderFactoryBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
/**
 * <p>
 * ServiceLoaderConfig
 * <p>
 *
 * @author 花行 (Rain)
 * @since 2025/7/19 19:34
 */
@Configuration
public class ServiceLoaderConfig {

    @Bean("serviceLoader")
    public ServiceLoaderFactoryBean checkRetryServiceLoader() {
        ServiceLoaderFactoryBean factoryBean = new ServiceLoaderFactoryBean();
        factoryBean.setServiceType(CheckRetryService.class);
        return factoryBean;
    }
}
