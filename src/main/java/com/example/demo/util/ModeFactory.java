package com.example.demo.util;

import com.example.demo.model.UserInfos;

/**
 * Author: wangchao
 * Time: 2018-10-12
 * Description: This is
 */
public class ModeFactory {

    public static UserInfos getNewUserInfo(String userName) {
        UserInfos u = new UserInfos();
        u.setName(userName);
        u.setHeaderimg("");
        u.setNickname("1231");

        u.setPhone("15128486957");
        u.setRegistertime(System.currentTimeMillis() + "");
        return u;
    }

}
