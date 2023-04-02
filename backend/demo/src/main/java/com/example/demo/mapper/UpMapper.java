package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.pojo.article.ThumbsUp;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UpMapper extends BaseMapper<ThumbsUp> {
}
