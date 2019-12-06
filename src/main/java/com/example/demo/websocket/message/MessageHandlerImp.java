package com.example.demo.websocket.message;

import com.example.demo.util.GsonUtil;
import com.example.demo.util.OutputUtil;
import com.example.demo.websocket.user.UserInfoBox;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Author: wangchao
 * Time: 2019-07-03
 * Description: This is 基础消息处理器
 */
public abstract class MessageHandlerImp<T> {
    /**
     * 统一处理消息
     *
     * @param strMessage 待处理的对象
     * @return 处理结果，返回数据
     */
    public Object handlerMessage(UserInfoBox mUserInfo, Object strMessage) {
        T getMessageBody = getBean(GsonUtil.toGsonString(strMessage));
        return onHandler(mUserInfo, getMessageBody);
    }

    /**
     * 具体处理工程
     *
     * @param t 解析后的实体
     * @return 处理结果，返回数据
     */
    public abstract Object onHandler(UserInfoBox mUserInfo, T t);

    /**
     * 获取实例对象
     *
     * @param strMessage
     * @return
     */
    private T getBean(String strMessage) {
        OutputUtil.d("getBean.strMessage:" + strMessage);
        Type genType = getClass().getGenericSuperclass();
        Type type = ((ParameterizedType) (genType)).getActualTypeArguments()[0];
        T message = GsonUtil.GsonToBean(strMessage, type);
        OutputUtil.d("getBean.message:");
        OutputUtil.d(message);
        return message;
    }
}
