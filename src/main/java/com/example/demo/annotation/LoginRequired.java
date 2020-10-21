package com.example.demo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author: wangchao
 * Time: 2018-09-28
 * Description: This is 在需要登录验证的Controller的方法上使用此注解,
 *     必须写在实现的方法上面，不能标记在接口中
 */
@Target({ElementType.METHOD})//可用在方法名上
@Retention(RetentionPolicy.RUNTIME)//运行时有效
public @interface LoginRequired {
}
