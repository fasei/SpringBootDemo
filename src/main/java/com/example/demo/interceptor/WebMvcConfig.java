package com.example.demo.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Author: wangchao
 * Time: 2018-09-29
 * Description: This is 添加拦截器
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 拦截所有请求，通过判断是否有 @LoginRequired 注解 决定是否需要登录
        //addPathPatterns 用户添加拦截规则
        //excludePathPatterns 用户排除拦截的
        registry.addInterceptor(authenticationInterceptor()).addPathPatterns("/**")
//                .excludePathPatterns("/swagger-resources/**","/error","/static/**")
        ;

        registry.addInterceptor(localResourcesInterceptor()).addPathPatterns("/*.png");

    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(currentUserMethodArgumentResolver());
    }

    @Bean
    public JsonReturnHandler jsonReturnHandler() {
        return new JsonReturnHandler();//初始化json过滤器
    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        returnValueHandlers.add(jsonReturnHandler());
    }

    @Bean
    public CurrentUserMethodArgumentResolver currentUserMethodArgumentResolver() {
        return new CurrentUserMethodArgumentResolver();
    }

    @Bean
    public AuthenticationInterceptor authenticationInterceptor() {
        return new AuthenticationInterceptor();
    }

    @Bean
    public LocalResourcesInterceptor localResourcesInterceptor() {
        return new LocalResourcesInterceptor();
    }
}