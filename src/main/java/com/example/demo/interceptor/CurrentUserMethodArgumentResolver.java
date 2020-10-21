package com.example.demo.interceptor;

import com.example.demo.annotation.CurrentUser;
import com.example.demo.constants.Constants;
import com.example.demo.model.UserInfos;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

/**
 * Author: wangchao
 * Time: 2018-09-28
 * Description: This is自定义解析器实现参数绑定增加方法注入，将含有 @CurrentUser 注解的方法参数注入当前登录用户
 */
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(UserInfos.class)
                && parameter.hasParameterAnnotation(CurrentUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        UserInfos user = (UserInfos) webRequest.getAttribute(Constants.CURRENT_USER, RequestAttributes.SCOPE_REQUEST);
        if (user != null) {
            return user;
        }
        throw new MissingServletRequestPartException(Constants.CURRENT_USER);
    }

}
