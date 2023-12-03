package com.example.article.service;


import com.example.article.pojo.Article;
import com.example.common.constants.response.ApiResponse;

import java.util.List;

/***
 * @author 睡醒继续做梦
 * @date 2023/3/30 17:50
 */
public interface ArticleService {

     ApiResponse<Void> add(String content,
                           String name,
                           String title);

     ApiResponse<Void> edit(Integer id,
                            String name,
                            String post,
                            String content,
                            String title,
                            Boolean show);

     ApiResponse<Void> delete(Integer id, String name);

     ApiResponse<Article> showbyid(Integer id);

     ApiResponse<List<Article>> showall();

    ApiResponse<List<Article>> showone(String post);
}
