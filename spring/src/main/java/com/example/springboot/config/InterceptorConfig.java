package com.example.springboot.config;

import com.example.springboot.common.intercept.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())
                .addPathPatterns("/**") // 拦截所有请求，通过判断token是否合法来决定是否需要登录
                .excludePathPatterns("/echarts/**","/user/**","/user/login", "/user/register", "/**/export", "/**/exportExample", "/**/import","/file/**","/menu/**","/achievement/**");
                // todo 上服务器记得注释掉这几行
    }

    @Bean
    public JwtInterceptor jwtInterceptor() {
        return new JwtInterceptor();
    }

}

