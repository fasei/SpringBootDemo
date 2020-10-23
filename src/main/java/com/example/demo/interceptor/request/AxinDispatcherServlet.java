package com.example.demo.interceptor.request;

import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Axin
 * @summary 自定义 DispatcherServlet 来分派 AxinHttpServletRequestWrapper
 */
public class AxinDispatcherServlet extends DispatcherServlet {
    /**
     * 包装成我们自定义的request
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @Override
    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        super.doDispatch(new AxinHttpServletRequestWrapper(request), response);
    }
}