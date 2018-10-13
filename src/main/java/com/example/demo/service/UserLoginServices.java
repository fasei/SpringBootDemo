package com.example.demo.service;

import com.example.demo.mapper.UserInfosMapper;
import com.example.demo.mapper.UserLoginMapper;
import com.example.demo.model.UserInfos;
import com.example.demo.model.UserInfosExample;
import com.example.demo.model.UserLogin;
import com.example.demo.model.UserLoginExample;
import com.example.demo.service.abs.AbsUserInfosServices;
import com.example.demo.service.abs.AbsUserLoginServices;
import com.example.demo.util.OutputUtil;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service("UserLoginServices")
public class UserLoginServices implements AbsUserLoginServices {
    @Autowired
    UserLoginMapper mUserLoginMapper;

    @Override
    public int insertNewUser(UserLogin mUserLogin) {
        int result = -1;
        if (mUserLogin == null) {
            return result;
        }
        UserLoginExample e = new UserLoginExample();
        e.createCriteria().andNameEqualTo(mUserLogin.getName() + "");
        List<UserLogin> mDataLists = mUserLoginMapper.selectByExample(e);
        if (mDataLists == null || mDataLists.size() == 0) {
            result = mUserLoginMapper.insert(mUserLogin);
            OutputUtil.log(result);
        } else {//用户已存在
            result = 2;
        }
        float a=333;
        BigDecimal d=new BigDecimal(a);
        return result;
    }

    @Override
    public int deleteUser(String mUserName) {
        UserLoginExample e = new UserLoginExample();
        e.createCriteria().andNameEqualTo(mUserName);
        return mUserLoginMapper.deleteByExample(e);
    }

    @Override
    public int updateUser(UserLogin mUserLogin) {
        return 0;
    }

    @Override
    public int getUserInfo(String mUserName, String mUserPassword) {
        UserLoginExample e = new UserLoginExample();
        e.createCriteria().andNameEqualTo(mUserName).andPswEqualTo(mUserPassword);
        List<UserLogin> mDataLists = mUserLoginMapper.selectByExample(e);
        return mDataLists.size() == 0 ? 0 : 1;
    }

    @Override
    public UserLogin getUserInfo(String mUserName) {
        UserLoginExample e = new UserLoginExample();
        e.createCriteria().andNameEqualTo(mUserName);
        List<UserLogin> mDataLists = mUserLoginMapper.selectByExample(e);
        return mDataLists.size() == 0 ? null : mDataLists.get(0);
    }
}
