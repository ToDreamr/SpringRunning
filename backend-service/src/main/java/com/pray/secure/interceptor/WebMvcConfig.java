package com.pray.secure.interceptor;

import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
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

    private Logger log = LoggerFactory.getLogger("WebMvcConfig");
    /**
     * 依赖于MVC的拦截器
     */
    @Resource
    private OnceRequestInterceptor interceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor);
    }
    /**
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        log.info("addCorsMappings");
        registry.addMapping("*");
        registry.addMapping("/**")//项目中的所有接口都支持跨域
                .allowedOriginPatterns("*")//所有地址都可以访问，也可以配置具体地址
                .allowCredentials(true)
                .allowedMethods("*")//"GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS"
                .maxAge(Integer.MAX_VALUE);// 跨域允许时间
    }
}
