package com.example.demo.interceptor;

import com.example.demo.annotation.LoginRequired;
import com.example.demo.constants.CurrentUserConstants;
import com.example.demo.exception.MyException;
import com.example.demo.log.LogUtil;
import com.example.demo.model.UserInfos;
import com.example.demo.service.UserInfoServices;
import com.example.demo.util.TokenUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * Author: wangchao
 * Time: 2018-09-28
 * Description: This is 判断token的拦截器
 */
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {
    public final static String Authorization = "Authorization";
    @Autowired
    private UserInfoServices userService;

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
        // 判断接口是否需要登录
        LoginRequired methodAnnotation = method.getAnnotation(LoginRequired.class);
        // 有 @LoginRequired 注解，需要认证
        if (methodAnnotation != null) {
            // 判断是否存在令牌信息，如果存在，则允许登录
//            String accessToken = request.getParameter(ACCESS_TOKEN);
            String accessToken = request.getHeader(Authorization);
            if (null == accessToken) {
                throw new MyException(555, "无token，请重新登录");
            }
            Claims claims = null;
            try {
                claims = TokenUtil.parseJWT(accessToken);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (claims == null) {
                throw new MyException(33, "无效token");
            }
            String userStrID = claims.getId();
            Long id = Long.parseLong(userStrID);

            UserInfos user = userService.getUserInfo(id);
            if (user == null) {
                throw new MyException(666, "用户不存在，请重新登录");
            }
            // 当前登录用户@CurrentUser
            request.setAttribute(CurrentUserConstants.CURRENT_USER, user);
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
        LogUtil.getPlatformLogger().info("AuthenticationInterceptor.postHandle");

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
        LogUtil.getPlatformLogger().info("AuthenticationInterceptor.afterCompletion");
    }
}
