package com.example.demo.exception;

import com.example.demo.constants.ResultCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * 注意ControllerAdvice只能捕获到全局Controller范围内的，之外的异常就无法捕获了，
 * 如filter中抛出异常的话，ControllerAdvice是无法捕获的。
 * 这时候你就需要按照官方文档中的方法，实现 ErrorController并注册为controller。
 */
@RestControllerAdvice//拦截异常
public class GlobalExceptionHandler {

    /**
     * 全局异常捕捉处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)//指定处理哪种异常（可指定多个）
    @ResponseStatus(HttpStatus.OK)//指定返回的http状态码（具体可查看HttpStatus这个类）
    public Map ExceptionHandler(Exception e) {
        e.printStackTrace();
        MyException myException = new MyException();

        if (e instanceof MissingServletRequestParameterException) { //缺少参数
            myException.setResultCode(ResultCode.PARAM_NOT_COMPLETE);
        } else if (e instanceof HttpRequestMethodNotSupportedException) {//请求方式未提供
            myException.setResultCode(ResultCode.INTERFACE_ADDRESS_ERROR);
        } else {
            myException.setResultCode(ResultCode.SPECIFIED_QUESTIONED_USER_NOT_EXIST);
        }

        return myErrorHandler(myException);
    }

    /**
     * 拦截捕捉自定义异常 MyException.class
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = MyException.class)
    @ResponseStatus(HttpStatus.OK)
    public Map myErrorHandler(MyException ex) {
        Map map = new HashMap();
        map.put("code", ex.getCode());
        map.put("msg", ex.getMsg());
        return map;
    }

}
