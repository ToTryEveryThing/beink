package com.example.article.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.article.pojo.ThumbsUp;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UpMapper extends BaseMapper<ThumbsUp> {
}
