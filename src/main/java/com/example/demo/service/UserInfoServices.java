package com.example.demo.service;

import com.example.demo.mapper.UserInfosMapper;
import com.example.demo.model.UserInfos;
import com.example.demo.model.UserInfosExample;
import com.example.demo.service.abs.AbsUserInfosServices;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("UserInfoServices")
public class UserInfoServices implements AbsUserInfosServices {

    @Autowired
    UserInfosMapper mapper;

    @Override
    public int setUser(UserInfos userinfo) {
        return mapper.insert(userinfo);
    }

    @Override
    public int deleteUser(long userID) {
        return mapper.deleteByPrimaryKey(userID);
    }

    @Override
    public int updateUser(UserInfos user) {
        return mapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public List<UserInfos> getUserList(int pageNum, int pageSize) {
        //将参数传给这个方法就可以实现物理分页了，非常简单。
        PageHelper.startPage(pageNum, pageSize);
        UserInfosExample e = new UserInfosExample();
        e.createCriteria().andNameIsNotNull();
        List<UserInfos> userinfos = mapper.selectByExample(e);
        return userinfos;
    }

    public UserInfos getUserInfo(String name) {
        UserInfosExample e = new UserInfosExample();
        e.createCriteria().andNameEqualTo(name);
        List<UserInfos> userinfos = mapper.selectByExample(e);
        return userinfos.size() == 0 ? null : userinfos.get(0);
    }

    @Override
    public UserInfos getUserInfo(long id) {
        return mapper.selectByPrimaryKey(id);
    }
}
