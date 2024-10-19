package com.pray.secure.interceptor;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebMvcConfig
 *
 * @author Cotton Eye Joe
 * @since 2024/10/19 23:35
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 依赖于MVC的拦截器
     */
    @Resource
    private OnceRequestInterceptor interceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor);
    }
}
