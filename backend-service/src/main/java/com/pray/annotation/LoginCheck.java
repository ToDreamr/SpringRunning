package com.pray.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * LoginCheck
 *
 * @author Cotton Eye Joe
 * 2024/9/3 14:29
 * 登录校验注解，实际上似乎使用OncePerRequestFilter过滤器之后
 * 不需要使用这个注解啊，但是尝试实现一下
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginCheck {
    String authorization() default "";
    int isLogin() default 0;//0:未登录，1：登录，2；过期
}
