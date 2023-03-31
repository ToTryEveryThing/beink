package com.example.demo.service.web.article;

import com.example.demo.controller.common.Result;

/***
 * @author 睡醒继续做梦
 * @date 2023/3/30 17:50
 */
public interface articleService {

     Result add( String content,
                 String name,
                 String title);

     Result edit( Integer id,
                  String name,
                  String post,
                  String content,
                  String title);

     Result delete( Integer id, String name);

     Result showbyid( Integer id);

     Result showall();

    Result showone(String post);
}
