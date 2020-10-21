package com.example.demo.websocket;

import com.example.demo.constants.Constants;
import com.example.demo.util.GsonUtil;
import com.example.demo.util.OutputUtil;
import com.example.demo.websocket.message.HandlerBox;
import com.example.demo.websocket.message.Message;
import com.example.demo.websocket.message.MessageCenter;
import com.example.demo.websocket.model.ResultBody;
import com.example.demo.websocket.user.GlobalUserManager;
import com.example.demo.websocket.user.MessageManage;
import com.example.demo.websocket.user.UserInfoBox;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.lang.reflect.Method;

/**
 * Author: wangchao
 * Time: 2019-06-24
 * Description: This is
 */
@ServerEndpoint(value = Constants.WebSocketPath)
@Component
public class WebSocketEndPoint {
    MessageManage mMessageManager = new MessageManage();
    GlobalUserManager mUserManager = GlobalUserManager.getInstance();
    UserInfoBox mUserInfo = new UserInfoBox();

    public WebSocketEndPoint() {
    }

    @OnOpen
    public void onOpen(Session session) {
        //像刷新这种，id一样，session不一样，后面的覆盖前面的

        OutputUtil.d("onOpen.identifier:" + session.getId());
        OutputUtil.d("onOpen.identifier:" + this);
        session.getAsyncRemote().sendText("onOpen.你大爷的！！！");
    }

    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        OutputUtil.d("发生错误");
        error.printStackTrace();

    }

    @OnClose
    public void onClose(Session session) {
        OutputUtil.d("onClose.identifier:");
        if (mUserInfo.isLogin()) {
            mUserManager.offLineUser(mUserInfo.getUserName());
        }
        mUserInfo = null;
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        OutputUtil.d("onMessage.message:" + message);
        handleMessage(session, message);
    }

    public void handleMessage(Session session, String message) {
        long startTime = System.currentTimeMillis();
        ThreadPools.getInstance().run(() -> { //使用线程池异步处理消息
            try {
                if (message.length() < 20 || !message.startsWith("{") || !message.endsWith("}")) {
                    OutputUtil.d("数据格式不正确！");
                    return;
                }
                Message msg = GsonUtil.GsonToBean(message, Message.class); //获取基本消息
                HandlerBox handlerInfo = MessageCenter.getHandlerInfo(msg.getCommand()); //获取消息处理器信息
                if (handlerInfo == null) {
                    OutputUtil.d("暂不支持的消息类型：");
                    return;
                }
                Class clazz = Class.forName(handlerInfo.getHandlerClass().getName());//反射获取处理器实例
                Object t = clazz.newInstance();
                //第一个参数是被调用方法的名称，后面接着这个方法的形参类型
                Method setFunc = clazz.getMethod("handlerMessage", UserInfoBox.class, Object.class);
                //取得方法后即可通过invoke方法调用，传入被调用方法所在类的对象和实参,对象可以通过cls.newInstance取得
                ResultBody resultBody = (ResultBody) setFunc.invoke(t, mUserInfo, msg.getData());//反射执行处理器方法
                Message response = Message.getInstance(msg.getMid(), handlerInfo.getResultCode(), resultBody);//构造响应器
                OutputUtil.d("响应消息：");
                OutputUtil.d(response);
                session.getAsyncRemote().sendText(GsonUtil.toGsonString(response));//响应数据
            } catch (Exception e) {
                e.printStackTrace();
            }
            long endTime = System.currentTimeMillis();
            OutputUtil.d("处理此消息耗时：" + (endTime - startTime));
        });
    }


}
