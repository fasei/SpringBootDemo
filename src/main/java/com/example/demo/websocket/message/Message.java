package com.example.demo.websocket.message;

import com.alibaba.druid.sql.visitor.functions.Now;
import com.example.demo.websocket.model.ResultBody;

/**
 * Author: wangchao
 * Time: 2019-07-03
 * Description: This is
 */
public class Message<T> extends Header {

    private T data;

    public Message() {
    }

    public Message(int mid, int command, String version, T data) {
        super(mid, command, version);
        this.data = data;
    }

    public static Message getInstance(int id, int command, ResultBody result) {
        return new Message(id, command, NowVersion, result);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
