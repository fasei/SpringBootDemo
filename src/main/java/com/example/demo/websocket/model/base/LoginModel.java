package com.example.demo.websocket.model.base;

/**
 * Author: wangchao
 * Time: 2019-07-03
 * Description: This is
 */
public class LoginModel {
    private String name;
    private String password;

    public LoginModel() {
    }

    public LoginModel(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
