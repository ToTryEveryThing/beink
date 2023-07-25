package com.example.demo.mapper.article;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.pojo.article.article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/***
 * @author 睡醒继续做梦
 * @date 2023/3/30 17:32
 */

@Mapper
public interface ArticleMapper extends BaseMapper<article> {

//    void selectById(@Param("id") int id);

    int insertSelective(article article);

    int updateSelective(article article);

}
