package com.example.user.mapper;
import java.util.List;

import com.example.user.domain.Chat;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 睡醒继续做梦
* @description 针对表【chat】的数据库操作Mapper
* @createDate 2023-03-13 14:24:34
* @Entity com.example.demo.pojo.Chat
*/
@Mapper
public interface ChatMapper extends BaseMapper<Chat> {
    List<Chat> selectAllById(@Param("id") Integer id);
}




