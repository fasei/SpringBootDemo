package com.example.demo.bean.request;

import com.example.demo.bean.Base;

import java.io.Serializable;

/**
 * Author: wangchao
 * Time: 2018-10-11
 * Description: This is
 */
public class LoginRequest extends Base implements Serializable {
    private String username;
    private String password;

    public LoginRequest() {
    }

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
