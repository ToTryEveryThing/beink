package com.example.article.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.article.pojo.Article;
import org.apache.ibatis.annotations.Mapper;

/***
 * @author 睡醒继续做梦
 * @date 2023/3/30 17:32
 */

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

//    void selectById(@Param("id") int id);

    int insertSelective(Article article);

    int updateSelective(Article article);

}
