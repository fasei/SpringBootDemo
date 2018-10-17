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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("UserServices")
public class UserServices implements AbsUserInfosServices, AbsUserLoginServices {
    @Autowired
    UserLoginMapper mUserLoginMapper;

    @Autowired
    UserInfosMapper mUserInfosMapper;

    public int setUser(UserInfos userinfo) throws Exception {
        return mUserInfosMapper.insert(userinfo);
    }

    @Override
    @Transactional
    public int addNewUser(UserInfos mUserInfos, UserLogin mUserLogin) throws Exception {
        int result = -1;
        OutputUtil.log("注册新用户：");
        OutputUtil.log(mUserInfos);
        OutputUtil.log(mUserLogin);
        UserLoginExample e = new UserLoginExample();
        e.createCriteria().andNameEqualTo(mUserLogin.getName() + "");
        List<UserLogin> mDataLists = mUserLoginMapper.selectByExample(e);
        if (mDataLists.size() == 0) {//没有注册用户
            mUserLoginMapper.insert(mUserLogin);
            mUserInfosMapper.insert(mUserInfos);
            result = 1;
            OutputUtil.log("注册成功！");
        } else {//用户已存在
            result = 2;
            OutputUtil.log("该用户已注册！");
        }
        return result;
    }

    @Override
    public int deleteUser(long userID) throws Exception {
        return mUserInfosMapper.deleteByPrimaryKey(userID);
    }

    @Override
    public int updateUserInfos(UserInfos user) throws Exception {
        return 0;
    }

    @Override
    public List<UserInfos> getUserInfos(int pageNum, int pageSize) throws Exception {
        return null;
    }

    public int updateUser(UserInfos user) throws Exception {
        return mUserInfosMapper.updateByPrimaryKeySelective(user);
    }

    public List<UserInfos> getUserList(int pageNum, int pageSize) throws Exception {
        //将参数传给这个方法就可以实现物理分页了，非常简单。
        PageHelper.startPage(pageNum, pageSize);
        UserInfosExample e = new UserInfosExample();
        e.createCriteria().andNameIsNotNull();
        List<UserInfos> userinfos = mUserInfosMapper.selectByExample(e);
        return userinfos;
    }

    public UserInfos getUserInfo(String name) throws Exception {
        UserInfosExample e = new UserInfosExample();
        e.createCriteria().andNameEqualTo(name);
        List<UserInfos> userinfos = mUserInfosMapper.selectByExample(e);
        return userinfos.size() == 0 ? null : userinfos.get(0);
    }

    @Override
    public UserInfos getUserInfo(long id) throws Exception {
        return mUserInfosMapper.selectByPrimaryKey(id);
    }


    @Transactional
    public int insertNewUser(UserLogin mUserLogin) throws Exception {
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
        return result;
    }

    @Override
    public int deleteUser(String mUserName) throws Exception {
        UserLoginExample e = new UserLoginExample();
        e.createCriteria().andNameEqualTo(mUserName);
        return mUserLoginMapper.deleteByExample(e);
    }

    @Override
    public int updateUser(UserLogin mUserLogin) throws Exception {
        return 0;
    }

    @Override
    public int getUserInfo(String mUserName, String mUserPassword) throws Exception {
        UserLoginExample e = new UserLoginExample();
        e.createCriteria().andNameEqualTo(mUserName).andPswEqualTo(mUserPassword);
        List<UserLogin> mDataLists = mUserLoginMapper.selectByExample(e);
        return mDataLists.size() == 0 ? 0 : 1;
    }

    @Override
    public UserLogin getUserLogin(String mUserName) throws Exception {
        UserLoginExample e = new UserLoginExample();
        e.createCriteria().andNameEqualTo(mUserName);
        List<UserLogin> mDataLists = mUserLoginMapper.selectByExample(e);
        return mDataLists.size() == 0 ? null : mDataLists.get(0);
    }
}
