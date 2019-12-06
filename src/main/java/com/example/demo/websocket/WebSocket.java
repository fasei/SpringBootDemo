package com.example.demo.websocket;

import java.io.Serializable;

/**
 * Author: wangchao
 * Time: 2019-06-24
 * Description: This is
 */
public class WebSocket implements Serializable {
    public static final int STATUS_AVAILABLE       = 0;
    public static final int STATUS_UNAVAILABLE     = 1;

    private String identifier;
    private int status;

    public WebSocket() {
    }

    public WebSocket(String identifier, int status) {
        this.identifier = identifier;
        this.status = status;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
