package com.example.demo.service.web;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.controller.common.Result;

/**
 * @author 睡醒继续做梦
 * @date 2023/2/16
 */
public interface DiscussService {
    public Result addReply(String content,String postName,
                           String postIndex,String userName);
    public JSONObject showReply(String postName, String postIndex, Integer page);

    public Result delReply(String userName, Integer id);
}