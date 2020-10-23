package com.example.demo.interceptor;

import com.example.demo.interceptor.request.AxinHttpServletRequestWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.UUID;

/**
 * Author: wangchao
 * Time: 2019-12-06
 * Description: This is
 */
@Component
public class LogInterceptor implements HandlerInterceptor {

    private final static String MarkTime = "MarkTime";

    private final static String REQUEST_ID = "requestId";
    private static final Logger LOGGER = LoggerFactory.getLogger(LogInterceptor.class.getSimpleName());

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String remoteIp = httpServletRequest.getRemoteAddr();
        String uuid = UUID.randomUUID().toString();
        MDC.put(REQUEST_ID, uuid + '_' + remoteIp);

        LOGGER.info("--------------->");
        LOGGER.info("Come in a new baby!!!");

       /*
       Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            String value = httpServletRequest.getHeader(name);
            LOGGER.info("headers   key:{},value:{}",name,value);
        }
        */

        //获取请求参数
        String queryString = httpServletRequest.getQueryString();
        if (!StringUtils.isEmpty(queryString)) {
            LOGGER.info("请求参数:{}", queryString);
        }

        httpServletRequest.setAttribute(MarkTime, System.currentTimeMillis());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        long currentTime = System.currentTimeMillis();
        Long startTime = (Long) httpServletRequest.getAttribute(MarkTime);
        LOGGER.info("Use time is:{}ms", (currentTime - startTime));
        LOGGER.info("Go out the baby!!!\n");
        MDC.remove(REQUEST_ID);
    }

}
