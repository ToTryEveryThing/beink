package com.example.demo.service.web.article;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.controller.common.Result;

public interface thumbsUp {
    public Result up(String userName, Integer articleId);
    public Result down(String userName, Integer articleId);
    public JSONObject discussStatus(String userName,String ids);

}