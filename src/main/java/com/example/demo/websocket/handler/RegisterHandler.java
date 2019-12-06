package com.example.demo.websocket.handler;

import com.example.demo.websocket.message.MessageHandlerImp;
import com.example.demo.websocket.model.ResultBody;
import com.example.demo.websocket.model.base.RegisterModel;
import com.example.demo.websocket.model.base.UserInfoModel;
import com.example.demo.websocket.user.UserInfoBox;

/**
 * Author: wangchao
 * Time: 2019-07-04
 * Description: This is
 */
public class RegisterHandler extends MessageHandlerImp<RegisterModel> {
    @Override
    public Object onHandler(UserInfoBox mUserInfo, RegisterModel registerModel) {
        mUserInfo.setUserName("666");
        return new ResultBody(0, new UserInfoModel());
    }
}
