package com.pray.config;

import com.pray.entity.dto.AuthorizeDTO;
import com.pray.entity.po.LoginUser;
import com.pray.service.dao.UserService;
import com.pray.utils.JwtUtils;
import com.pray.common.Result;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * SecurityConfig
 *
 * @author Cotton Eye Joe
 * @since 2024/10/19 22:45
 */

@Configuration
public class SecurityConfig  {

    @Resource
    UserService userService;

    @Resource
    JwtUtils jwtUtils;

    @Resource
    StringRedisTemplate stringRedisTemplate;


    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(conf -> conf.requestMatchers("/**").anonymous())
                .formLogin
                        (
                                conf -> conf.loginProcessingUrl("/auth/login").permitAll()
                                        .failureHandler((request, response, exception) -> {
                                            response.getWriter().write(Result.fail(401, "登录失败").JsonResult());
                                        })
                                        .successHandler((request, response, authentication) -> {
                                            LoginUser loginUser = (LoginUser) authentication.getPrincipal();
                                            String token = jwtUtils.createJwtWithKeyParam(loginUser, loginUser.getUsername());
                                            AuthorizeDTO authorizeDTO = new AuthorizeDTO();
                                            authorizeDTO.setUsername(loginUser.getUsername());
                                            authorizeDTO.setToken(token);
                                            stringRedisTemplate.opsForValue().setIfAbsent(loginUser.getUsername(), String.valueOf(authorizeDTO));
                                            response.getWriter().write(Result.ok(authorizeDTO).JsonResult());
                                        })
                        )
                .build();
    }

}
