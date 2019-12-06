package com.example.demo.websocket.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import javax.websocket.Session;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Author: wangchao
 * Time: 2019-07-03
 * Description: This is 总用户管理类
 */
public class GlobalUserManager implements UserManagerImp {
    private Map<String, UserInfoBox> onLineSession = new ConcurrentHashMap<>();
    private OnLineCount onLineCount = new OnLineCount();

    @Override
    public void onLineUser(String id, UserInfoBox userInfoBox) {
        onLineSession.put(id, userInfoBox);
        onLineCount.safeAddCount();
    }

    @Override
    public void offLineUser(String id) {
        onLineSession.remove(id);
        onLineCount.safeMinusCount();
    }

    @Override
    public int getOnLineUsers() {
        return onLineCount.getCount();
    }

    @Override
    public boolean isUserOnline(String id) {
        return onLineSession.get(id) == null;
    }

    private static class SingletonClassInstance {
        private static final GlobalUserManager instance = new GlobalUserManager();
    }

    public static GlobalUserManager getInstance() {
        return SingletonClassInstance.instance;
    }
}
