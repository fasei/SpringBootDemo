package com.example.demo.websocket.message;

/**
 * Author: wangchao
 * Time: 2019-07-04
 * Description: This is
 */
public class HandlerBox {
    private int resultCode;
    private Class handlerClass;

    public HandlerBox(int resultCode, Class handlerClass) {
        this.resultCode = resultCode;
        this.handlerClass = handlerClass;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public Class getHandlerClass() {
        return handlerClass;
    }

    public void setHandlerClass(Class handlerClass) {
        this.handlerClass = handlerClass;
    }
}
