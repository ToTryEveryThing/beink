package com.example.demo.mapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 小猖娃
* @description 针对表【user】的数据库操作Mapper
* @createDate 2023-02-04 12:01:35
* @Entity com.example.demo.pojo.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {
    int deleteByAccount(@Param("account") String account);
    int insertSelective(User user);
}




