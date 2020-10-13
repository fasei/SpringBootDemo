package com.example.demo.exception;

import com.example.demo.constants.ResultCode;
import com.example.demo.util.OutputUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

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
        OutputUtil.d("捕获controller异常");
        e.printStackTrace();
        MyException myException = new MyException();

        if (e instanceof MissingServletRequestParameterException||e instanceof MissingServletRequestPartException) { //缺少参数
            myException.setResultCode(ResultCode.PARAM_NOT_COMPLETE);
        } else if (e instanceof HttpRequestMethodNotSupportedException) {//请求方式未提供
            myException.setResultCode(ResultCode.INTERFACE_ADDRESS_ERROR);
        }else if(e instanceof MaxUploadSizeExceededException){
            myException.setResultCode(ResultCode.MAX_UPLOADSIXE);
        } else {
            myException.setResultCode(ResultCode.SPECIFIED_QUESTIONED_USER_NOT_EXIST);
        }

        return myErrorHandler(myException);
    }

//    /**
//     * 拦截捕捉自定义异常 MyException.class
//     *
//     * @param ex
//     * @return
//     */
//    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
//    @ResponseStatus(HttpStatus.OK)
//    public Map myMaxUploadSizeHandler(MaxUploadSizeExceededException ex) {
//        OutputUtil.d("controller主动抛出异常");
//        MyException myException = new MyException();
//        myException.setResultCode(ResultCode.MAX_UPLOADSIXE);
//
//        Map map = new HashMap();
//        map.put("code", myException.getCode());
//        map.put("msg", myException.getMsg());
//        return map;
//    }

    /**
     * 拦截捕捉自定义异常 MyException.class
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = MyException.class)
    @ResponseStatus(HttpStatus.OK)
    public Map myErrorHandler(MyException ex) {
        OutputUtil.d("controller主动抛出异常");
        Map map = new HashMap();
        map.put("code", ex.getCode());
        map.put("msg", ex.getMsg());
        return map;
    }

}
