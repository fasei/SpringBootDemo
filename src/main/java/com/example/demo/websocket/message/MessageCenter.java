package com.example.demo.websocket.message;

import com.example.demo.util.OutputUtil;
import com.example.demo.websocket.handler.LoginHandler;
import com.example.demo.websocket.handler.RegisterHandler;
import com.example.demo.websocket.message.type.BaseMessageType;
import org.apache.tomcat.jni.Error;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: wangchao
 * Time: 2019-07-04
 * Description: This is 消息中心
 */
public class MessageCenter {
    public static Map<Integer, HandlerBox> handlerCenter = new HashMap<>();

    /**
     * 需要初始化消息处理器
     */
    public static void init() {
        handlerCenter.clear();
        BaseMessageType base = new BaseMessageType();
        configMessageCenter(base.getLogin(), new HandlerBox(base.getLoginResult(), LoginHandler.class));//登录处理器
        configMessageCenter(base.getRegister(), new HandlerBox(base.getRegisterResult(), RegisterHandler.class));//登录处理器

    }

    public static void configMessageCenter(Integer key, HandlerBox box) {
        if (handlerCenter.containsKey(key)) {
            throw new RuntimeException("重复添加key：" + key);
        }
        handlerCenter.put(key, box);
    }

    public static HandlerBox getHandlerInfo(int key) {
        return handlerCenter.get(key);
    }

}
