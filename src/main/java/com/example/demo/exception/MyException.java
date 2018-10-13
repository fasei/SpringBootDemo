package com.example.demo.exception;

import com.example.demo.constants.ResultCode;

public class MyException extends RuntimeException {
    private int code;
    private String msg;

    public MyException() {
    }

    public MyException(int code, String msg) {
        this.code = code;
        this.msg = msg;
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

    /**
     * 设置异常信息
     * @param code
     */
    public void setResultCode(ResultCode code) {
        this.code = code.code();
        this.msg = code.message();
    }

    @Override
    public String toString() {
        return "MyException{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
