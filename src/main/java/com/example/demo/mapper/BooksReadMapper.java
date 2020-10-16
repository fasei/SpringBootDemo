package com.example.demo.mapper;

import com.example.demo.model.BooksRead;
import com.example.demo.model.BooksReadExample;
import com.example.demo.model.BooksReadWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BooksReadMapper {
    int countByExample(BooksReadExample example);

    int deleteByExample(BooksReadExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BooksReadWithBLOBs record);

    int insertSelective(BooksReadWithBLOBs record);

    List<BooksReadWithBLOBs> selectByExampleWithBLOBs(BooksReadExample example);

    List<BooksRead> selectByExample(BooksReadExample example);

    BooksReadWithBLOBs selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BooksReadWithBLOBs record, @Param("example") BooksReadExample example);

    int updateByExampleWithBLOBs(@Param("record") BooksReadWithBLOBs record, @Param("example") BooksReadExample example);

    int updateByExample(@Param("record") BooksRead record, @Param("example") BooksReadExample example);

    int updateByPrimaryKeySelective(BooksReadWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(BooksReadWithBLOBs record);

    int updateByPrimaryKey(BooksRead record);
}