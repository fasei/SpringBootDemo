package com.example.demo.bean.response;

import com.example.demo.bean.Base;
import com.example.demo.constants.ResultCode;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;

/**
 * Author: wangchao
 * Time: 2018-10-10
 * Description: This is 通用的返回结果样式
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Result<T> extends Base<T> implements Serializable {
    private int code;
    private String msg;
    private T data;

    /**
     * 结果成功，无参
     *
     * @return
     */
    public static Result getInstance() {
        Result result = new Result();
        return result;
    }

    /**
     * 结果成功，无参
     *
     * @return
     */
    public static Result success() {
        Result result = new Result();
        result.setResultCode(ResultCode.SUCCESS);
        return result;
    }

    /**
     * 结果成功，带参数
     *
     * @param data
     * @return
     */
    public static Result success(Object data) {
        Result result = new Result();
        result.setResultCode(ResultCode.SUCCESS);
        result.setData(data);
        return result;
    }

    /**
     * 结果失败
     *
     * @param resultCode 失败原因
     * @return
     */
    public static Result failure(ResultCode resultCode) {
        Result result = new Result();
        result.setResultCode(resultCode);
        return result;
    }

    public Result() {
    }

    public Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setResultCode(ResultCode code) {
        this.code = code.code();
        this.msg = code.message();
    }

}
