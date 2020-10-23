package com.example.demo.interceptor;

import com.example.demo.constants.Constants;
import com.example.demo.interceptor.request.AxinDispatcherServlet;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.*;

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
        registry.addInterceptor(new LogInterceptor());
        // 拦截所有请求，通过判断是否有 @LoginRequired 注解 决定是否需要登录
        //addPathPatterns 用户添加拦截规则
        //excludePathPatterns 用户排除拦截的
        registry.addInterceptor(authenticationInterceptor()).addPathPatterns(Constants.BaseControllerPath + "/**").excludePathPatterns(Constants.BaseSocketPath + "/**")
//                .excludePathPatterns("/swagger-resources/**","/error","/static/**")
        ;
    }

//    @Bean
//    @Qualifier(DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_BEAN_NAME)
//    public DispatcherServlet dispatcherServlet() {
//        return new AxinDispatcherServlet();
//    }

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