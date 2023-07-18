package com.example.demo.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.pojo.user.web;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface WebMapper extends BaseMapper<web> {
}
