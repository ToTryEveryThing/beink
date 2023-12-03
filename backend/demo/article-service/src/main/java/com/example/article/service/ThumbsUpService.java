package com.example.article.service;

import com.alibaba.fastjson.JSONObject;
import com.example.common.constants.response.ApiResponse;


public interface ThumbsUpService {
    public ApiResponse<Void> up(String userName, Integer articleId);
    public ApiResponse<Void> down(String userName, Integer articleId);
    public JSONObject discussStatus(String userName,String ids);

}
