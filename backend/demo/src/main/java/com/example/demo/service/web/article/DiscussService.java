package com.example.demo.service.web.article;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.controller.common.ApiResponse;

/**
 * @author 睡醒继续做梦
 * @date 2023/2/16
 */
public interface DiscussService {
    public ApiResponse<Void> addReply(String content,
                                      Integer articleId, String userName);
    public JSONObject showReply(Integer articleId, Integer page);

    public ApiResponse<Void> delReply(String userName, Integer id);
}
