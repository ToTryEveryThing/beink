package com.example.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.example.common.pojo.Log;
import org.apache.ibatis.annotations.Mapper;

/***
 * @author 睡醒继续做梦
 * @date 2023/4/30 9:42
 */

@Mapper
public interface LogMapper extends BaseMapper<Log> {
}
