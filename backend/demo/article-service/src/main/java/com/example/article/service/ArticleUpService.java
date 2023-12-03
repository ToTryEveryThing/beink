package com.example.article.service;


import com.example.common.constants.response.ApiResponse;

/***
 * @author 睡醒继续做梦
 * @date 2023/4/2 10:38
 */
public interface ArticleUpService {

    ApiResponse<Void> changeUp(String userName, Integer articleId, Integer status);

    ApiResponse<Integer> getStatus(Integer articleId, String userName);

}
