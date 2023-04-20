package com.example.demo.service.web.article;

import com.example.demo.controller.common.ApiResponse;
import com.example.demo.pojo.article.article;

import java.util.List;

/***
 * @author 睡醒继续做梦
 * @date 2023/3/30 17:50
 */
public interface articleService {

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

     ApiResponse<article> showbyid(Integer id);

     ApiResponse<List<article>> showall();

    ApiResponse<List<article>> showone(String post);
}
