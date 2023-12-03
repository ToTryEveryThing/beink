package com.example.article.controller;


import com.alibaba.fastjson.JSONObject;
import com.example.article.service.DiscussService;
import com.example.common.config.limitApi.AccessLimit;
import com.example.common.config.operationLog.MyLog;
import com.example.common.constants.response.ApiResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author 睡醒继续做梦
 * @date 2023/2/16
 */
@RestController
public class DiscussController {


    @Resource
    private DiscussService discussService;

    @AccessLimit(seconds = 60*60,maxCount = 50)
    @PostMapping("/user/discuss/add/")
    @MyLog
    public ApiResponse<Void> add(@RequestParam String userName, @RequestParam Map<String ,String> map){
        String content = map.get("content");
        Integer articleId = Integer.valueOf(map.get("article_id"));
        return discussService.addReply(content,articleId,userName);
    }

    @AccessLimit(seconds = 60*60,maxCount = 50)
    @PostMapping("/user/discuss/show/")
    public JSONObject show(@RequestParam Map<String ,String> map){
        return discussService.showReply(Integer.valueOf(map.get("article_id")),
                Integer.valueOf(map.get("page")));
    }

    @PostMapping("/user/discuss/delete/")
    @MyLog
    public ApiResponse<Void> del(@RequestParam String userName, Integer id){
        return discussService.delReply(userName,id);
    }

}
