package com.example.demo.mapper;

import com.example.demo.model.UserInfos;
import com.example.demo.model.UserInfosExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserInfosMapper {
    int countByExample(UserInfosExample example);

    int deleteByExample(UserInfosExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UserInfos record);

    int insertSelective(UserInfos record);

    List<UserInfos> selectByExample(UserInfosExample example);

    UserInfos selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UserInfos record, @Param("example") UserInfosExample example);

    int updateByExample(@Param("record") UserInfos record, @Param("example") UserInfosExample example);

    int updateByPrimaryKeySelective(UserInfos record);

    int updateByPrimaryKey(UserInfos record);
}