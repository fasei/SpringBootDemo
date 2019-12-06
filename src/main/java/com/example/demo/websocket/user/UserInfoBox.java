package com.example.demo.websocket.user;

import com.github.pagehelper.StringUtil;

import javax.websocket.Session;

/**
 * Author: wangchao
 * Time: 2019-07-04
 * Description: This is
 */
public class UserInfoBox {
    private String userName = "";
    private Session session;


    public boolean isLogin() {
        return StringUtil.isEmpty(userName);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
