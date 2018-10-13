package com.example.demo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author: wangchao
 * Time: 2018-09-28
 * Description: This is
 */
@Target({ElementType.PARAMETER})//可用在方法的参数上
@Retention(RetentionPolicy.RUNTIME)//运行时有效
public @interface CurrentUser {
}
