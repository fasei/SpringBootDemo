package com.example.demo.service.abs;

import com.example.demo.model.UserInfos;
import com.example.demo.model.UserLogin;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Author: wangchao
 * Time: 2018-10-12
 * Description: This is 用户登录表
 */
@Mapper
public interface AbsUserLoginServices {
    /**
     * 删除用户信息
     *
     * @param mUserName
     * @return
     */
    @Transactional
    int deleteUser(String mUserName)throws Exception;

    /**
     * 更新用户信息
     *
     * @param mUserLogin
     * @return
     */
    @Transactional
    int updateUser(UserLogin mUserLogin)throws Exception;

    /**
     * 查询用户（登陆验证功能）
     *
     * @param mUserName
     * @param mUserPassword
     * @return
     */
    int getUserInfo(String mUserName, String mUserPassword)throws Exception;

    /**
     * 查询用户
     *
     * @param mUserName
     * @return
     */
    UserLogin getUserLogin(String mUserName)throws Exception;

}
