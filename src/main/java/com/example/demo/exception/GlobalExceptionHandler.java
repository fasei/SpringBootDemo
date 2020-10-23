package com.example.demo.exception;

import com.example.demo.constants.Constants;
import com.example.demo.constants.ResultCode;
import com.example.demo.util.ExcpUtil;
import com.example.demo.util.OutputUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 注意ControllerAdvice只能捕获到全局Controller范围内的，之外的异常就无法捕获了，
 * 如filter中抛出异常的话，ControllerAdvice是无法捕获的。
 * 这时候你就需要按照官方文档中的方法，实现 ErrorController并注册为controller。
 */
@RestControllerAdvice//拦截异常
public class GlobalExceptionHandler {

    Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

    /**
     * 全局异常捕捉处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)//指定处理哪种异常（可指定多个）
    @ResponseStatus(HttpStatus.OK)//指定返回的http状态码（具体可查看HttpStatus这个类）
    public Map ExceptionHandler(Exception e) {
        logger.info(ExcpUtil.buildErrorMessage(e));

        MyException myException = new MyException();

        if (e instanceof MissingServletRequestParameterException || e instanceof MissingServletRequestPartException) { //缺少参数
            myException.setResultCode(ResultCode.PARAM_NOT_COMPLETE);
        } else if (e instanceof HttpRequestMethodNotSupportedException) {//请求方式未提供
            myException.setResultCode(ResultCode.INTERFACE_ADDRESS_ERROR);
        } else if (e instanceof MaxUploadSizeExceededException) {
            myException.setResultCode(ResultCode.MAX_UPLOADSIXE);
        } else {
            myException.setResultCode(ResultCode.SPECIFIED_QUESTIONED_USER_NOT_EXIST);
        }

        return myErrorHandler(myException);
    }

    /**
     * 拦截捕捉自定义异常 MyException.class
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.OK)
    public Map myValidatedHandler(ConstraintViolationException exception) {
        logger.info(ExcpUtil.buildErrorMessage(exception));

        Map map = new HashMap();

        map.put(Constants.Result.Code, ResultCode.PARAM_IS_INVALID.code());
        map.put(Constants.Result.Msg, ResultCode.PARAM_IS_INVALID.message());

        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
        for (ConstraintViolation<?> item : violations) {
            //打印验证不通过的信息
            if (!StringUtils.isEmpty(item.getMessage())) {
                map.put(Constants.Result.Data, item.getMessage());
            }
        }
        return map;
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
        logger.info(ExcpUtil.buildErrorMessage(ex));

        Map map = new HashMap();
        map.put(Constants.Result.Code, ex.getCode());
        map.put(Constants.Result.Msg, ex.getMsg());
        return map;
    }

}
