package com.example.demo.util;

import com.example.demo.model.UserInfos;
import com.example.demo.model.UserLogin;

/**
 * Author: wangchao
 * Time: 2018-10-12
 * Description: This is
 */
public class ModeFactory {

    public static UserInfos createNewUserInfo(String userName) {
        UserInfos u = new UserInfos();
        u.setName(userName);
        u.setHeaderimg("");
        u.setNickname("王超");

        u.setPhone("15128296802");
        u.setRegistertime(System.currentTimeMillis() + "");
        return u;
    }

    public static UserLogin createNewUserLogin(String userName, String password) {
        UserLogin u = new UserLogin();
        u.setName(userName);
        u.setPsw(password);

        u.setRegistertime(System.currentTimeMillis() + "");
        return u;
    }

}
