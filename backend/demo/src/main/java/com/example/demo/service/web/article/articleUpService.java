package com.example.demo.service.web.article;

import com.example.demo.controller.common.ApiResponse;

/***
 * @author 睡醒继续做梦
 * @date 2023/4/2 10:38
 */
public interface articleUpService {

    ApiResponse<Void> changeUp(String userName, Integer articleId, Integer status);

    ApiResponse<Integer> getStatus(Integer articleId, String userName);

}
