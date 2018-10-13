package com.example.demo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author: wangchao
 * Time: 2018-09-29
 * Description: This is
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonFieldFilter {
    Class<?> type();//对哪个类的属性进行过滤
    String include() default  "";//包含哪些字段，即哪些字段可以显示
    String exclude() default "";//不包含哪些字段，即哪些字段不可以显示
}
