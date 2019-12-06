package com.example.demo.websocket.handler;

import com.example.demo.util.GsonUtil;
import com.example.demo.util.OutputUtil;
import com.example.demo.websocket.message.Message;
import com.example.demo.websocket.message.MessageHandlerImp;
import com.example.demo.websocket.model.ResultBody;
import com.example.demo.websocket.model.base.LoginModel;
import com.example.demo.websocket.user.GlobalUserManager;
import com.example.demo.websocket.user.UserInfoBox;

/**
 * Author: wangchao
 * Time: 2019-07-03
 * Description: This is 登录处理器
 */
public class LoginHandler extends MessageHandlerImp<LoginModel> {
    @Override
    public Object onHandler(UserInfoBox mUserInfo, LoginModel loginModel) {
        if (loginModel != null) {
            mUserInfo.setUserName("3333");
            GlobalUserManager.getInstance().onLineUser(mUserInfo.getUserName(), mUserInfo);
            return new ResultBody<Integer>(0, "666");
        } else {
            return new ResultBody<Integer>(0, 666);
        }
    }

/*
    public static void main(String ar[]) {
        String a = "{\n" +
                "\t\"data\":{\n" +
                "\t\t\"code\":0,\n" +
                "\t\t\"errorMessage\":\"666\"\n" +
                "\t},\n" +
                "\t\"mid\":666,\n" +
                "\t\"command\":2,\n" +
                "\t\"version\":\"V1.0\"\n" +
                "}";
        Message mm=GsonUtil.GsonToBean(a,Message.class);
        OutputUtil.d(mm);
    }
*/

}
