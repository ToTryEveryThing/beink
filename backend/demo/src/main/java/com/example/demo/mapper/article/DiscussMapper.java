package com.example.demo.mapper.article;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.pojo.article.Discuss;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 小猖娃
* @description 针对表【discuss】的数据库操作Mapper
* @createDate 2023-02-16 09:50:33
* @Entity com.example.demo.pojo.article.Discuss
*/
@Mapper
public interface DiscussMapper extends BaseMapper<Discuss> {

    List<Discuss> selectAllByPostIndexAndPostName(@Param("postIndex") String postIndex, @Param("postName") String postName);

    int deleteByPostNameAndPostIndexAndUserName(@Param("postName") String postName, @Param("postIndex") String postIndex, @Param("userName") String userName);
}




