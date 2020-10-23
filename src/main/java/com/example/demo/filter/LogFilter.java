package com.example.demo.filter;


import com.example.demo.interceptor.request.AxinHttpServletRequestWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

//@Component
//@WebFilter(urlPatterns = "/*")
public class LogFilter implements Filter {
    private Logger LOGGER = LoggerFactory.getLogger(getClass().getSimpleName());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        servletRequest.getInputStream();
        servletRequest.getParameterMap();
        LOGGER.info("doFilter");

//        filterChain.doFilter(servletRequest, servletResponse);


        filterChain.doFilter(new AxinHttpServletRequestWrapper((HttpServletRequest) servletRequest), servletResponse);
        //获取请求参数
//        String queryString = httpServletRequest.getQueryString();
//        if (!StringUtils.isEmpty(queryString)) {
//            LOGGER.info("请求参数:{}", queryString);
//        }
        //获取请求body
//        byte[] bodyBytes = StreamUtils.copyToByteArray(httpServletRequest.getInputStream());
//        String body = new String(bodyBytes, httpServletRequest.getCharacterEncoding());
//        if (!StringUtils.isEmpty(body)) {
//            LOGGER.info("请求体：{}", body);
//        }

    }

    @Override
    public void destroy() {

    }
}
