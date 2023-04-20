package com.example.demo.service.web.article;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.controller.common.ApiResponse;

public interface thumbsUp {
    public ApiResponse<Void> up(String userName, Integer articleId);
    public ApiResponse<Void> down(String userName, Integer articleId);
    public JSONObject discussStatus(String userName,String ids);

}
