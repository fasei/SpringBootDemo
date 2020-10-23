package com.example.demo.interceptor;

import com.example.demo.annotation.LoginRequired;
import com.example.demo.constants.Constants;
import com.example.demo.exception.MyException;
import com.example.demo.model.UserInfos;
import com.example.demo.service.UserServices;
import com.example.demo.util.TokenUtil;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Date;

import static com.example.demo.constants.ResultCode.*;

/**
 * Author: wangchao
 * Time: 2018-09-28
 * Description: This is 判断token的拦截器
 */
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {
    Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

    public final static String Authorization = "Authorization";
    @Autowired
    private UserServices userService;

    /**
     * 在请求处理之前进行调用（Controller方法调用之前）
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        // 判断接口是否需要登录,注解需写在实现的方法上方
        LoginRequired methodAnnotation = method.getAnnotation(LoginRequired.class);

        // 有 @LoginRequired 注解，需要认证
        if (methodAnnotation != null) {
            // 判断是否存在令牌信息，如果存在，则允许登录
            String accessToken = request.getHeader(Authorization);
            if (null == accessToken) {
                throw new MyException(USER_NOT_LOGGED_IN.code(), USER_NOT_LOGGED_IN.message());
            }
            Claims claims = null;
            try {
                claims = TokenUtil.parseJWT(accessToken);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (claims == null) {
                throw new MyException(USER_TOKEN_ERROR.code(), USER_TOKEN_ERROR.message());
            }
            if (claims.getExpiration().before(new Date())) {  //验证是否到期
                throw new MyException(USER_TOKEN_EXPIRATION.code(), USER_TOKEN_EXPIRATION.message());
            }

            UserInfos user = null;
            try {
                String userStrID = claims.getId();
                Long id = Long.parseLong(userStrID);
                user = userService.getUserInfo(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (user == null) {
                throw new MyException(USER_NOT_EXIST.code(), USER_NOT_EXIST.message());
            }
            // 当前登录用户@CurrentUser
            request.setAttribute(Constants.CURRENT_USER, user);
            return true;
        }
        return true;
    }

    /**
     * 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    /**
     * 在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param e
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}
