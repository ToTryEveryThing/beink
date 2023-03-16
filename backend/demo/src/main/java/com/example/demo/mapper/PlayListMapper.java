package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.pojo.PlayerList;
import org.apache.ibatis.annotations.Mapper;

/***
 * @author 睡醒继续做梦
 * @date 2023/3/16 15:32
 */


@Mapper
public interface PlayListMapper extends BaseMapper<PlayerList> {
}
