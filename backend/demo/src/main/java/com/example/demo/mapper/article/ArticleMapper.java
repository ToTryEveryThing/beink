package com.example.demo.mapper.article;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.pojo.article.article;
import org.apache.ibatis.annotations.Mapper;

/***
 * @author 睡醒继续做梦
 * @date 2023/3/30 17:32
 */

@Mapper
public interface ArticleMapper extends BaseMapper<article> {
}
