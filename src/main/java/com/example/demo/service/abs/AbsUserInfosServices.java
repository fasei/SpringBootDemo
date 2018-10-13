package com.example.demo.service.abs;

import com.example.demo.model.UserInfos;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Author: wangchao
 * Time: 2018-10-12
 * Description: This is 用户信息表
 */
@Mapper
public interface AbsUserInfosServices {
    /**
     * 插入用户
     *
     * @param userinfo 用户信息
     * @return
     */
    int setUser(UserInfos userinfo);

    /**
     * 删除用户信息
     *
     * @param id
     * @return
     */
    int deleteUser(long id);

    /**
     * 更新用户信息
     *
     * @param user
     * @return
     */
    int updateUser(UserInfos user);

    /**
     * 分页获取用户信息
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<UserInfos> getUserList(int pageNum, int pageSize);

    UserInfos getUserInfo(String name);
    UserInfos getUserInfo(long id);

}
