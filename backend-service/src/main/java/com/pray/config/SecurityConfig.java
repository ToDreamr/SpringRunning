package com.pray.config;

import com.pray.entity.po.LoginUser;
import com.pray.entity.vo.response.AuthorizeVO;
import com.pray.service.dao.UserService;
import com.pray.utils.JwtUtils;
import com.pray.utils.Result;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

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
//                .cors(c -> {
//                    CorsConfigurationSource source = request -> {
//                        CorsConfiguration config = new CorsConfiguration();
//                        config.setAllowedOrigins(Arrays.asList("*"));
//                        config.setAllowedMethods(Arrays.asList("*"));
//                        return config;
//                    };
//                    c.configurationSource(source);
//                })
                .formLogin
                        (
                                conf -> conf.loginProcessingUrl("/auth/login").permitAll()
                                        .failureHandler((request, response, exception) -> {
                                            response.getWriter().write(Result.fail(401, "登录失败").JsonResult());
                                        })
                                        .successHandler((request, response, authentication) -> {
                                            LoginUser loginUser = (LoginUser) authentication.getPrincipal();
                                            String token = jwtUtils.createJwtWithKeyParam(loginUser, loginUser.getUsername());
                                            AuthorizeVO authorizeVO = new AuthorizeVO();
                                            authorizeVO.setUsername(loginUser.getUsername());
                                            authorizeVO.setToken(token);
                                            stringRedisTemplate.opsForValue().setIfAbsent(loginUser.getUsername(), String.valueOf(authorizeVO));
                                            response.getWriter().write(Result.ok(authorizeVO).JsonResult());
                                        })
                        )
                .build();
    }

}
