package com.example.demo.service.abs;

import com.example.demo.model.UserInfos;
import com.example.demo.model.UserLogin;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Author: wangchao
 * Time: 2018-10-12
 * Description: This is 用户信息表
 */
@Mapper
public interface AbsUserInfosServices {
    /**
     * 注册新用户
     *
     * @param mUserInfos 用户信息
     * @param mUserLogin 登录信息
     * @return
     */
    @Transactional
    int addNewUser(UserInfos mUserInfos, UserLogin mUserLogin) throws Exception;

    /**
     * 删除用户信息
     *
     * @param id
     * @return
     */
    int deleteUser(long id) throws Exception;

    /**
     * 更新用户信息
     *
     * @param user
     * @return
     */
    int updateUserInfos(UserInfos user) throws Exception;

    /**
     * 分页获取用户信息
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<UserInfos> getUserInfos(int pageNum, int pageSize) throws Exception;

    UserInfos getUserInfo(String name) throws Exception;

    UserInfos getUserInfo(long id) throws Exception;

}
