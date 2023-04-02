package com.example.demo.service.web.article;

import com.example.demo.controller.common.Result;

/***
 * @author 睡醒继续做梦
 * @date 2023/4/2 10:38
 */
public interface articleUpService {

    Result changeUp(String userName, Integer articleId, Integer status);

    Result getStatus(Integer articleId, String userName);

}
