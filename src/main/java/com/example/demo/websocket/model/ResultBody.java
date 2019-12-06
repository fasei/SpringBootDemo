package com.example.demo.websocket.model;

/**
 * Author: wangchao
 * Time: 2019-07-04
 * Description: This is 返回结果实体
 */
public class ResultBody<T> {
    private int code;
    private T data;
    private String errorMessage;

    public ResultBody(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public ResultBody(int code, String errorMessage) {
        this.code = code;
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
