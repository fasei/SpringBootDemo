package com.example.demo.websocket.user;

import javax.websocket.Session;

/**
 * Author: wangchao
 * Time: 2019-07-03
 * Description: This is 用户管理接口
 */
public interface UserManagerImp {
    /**
     * 在线用户（同时统计在线人数）
     *
     * @param id
     * @param userInfoBox
     */
    void onLineUser(String id, UserInfoBox userInfoBox);

    /**
     * 下线用户（同时统计在线人数）
     *
     * @param id
     */
    void offLineUser(String id);

    /**
     * 获取在线人数
     *
     * @return
     */
    int getOnLineUsers();

    /**
     * 检查用户是否在线
     *
     * @param id
     * @return
     */
    boolean isUserOnline(String id);
}
