package com.example.article.controller;


import com.alibaba.fastjson.JSONObject;
import com.example.article.service.ThumbsUpService;
import com.example.common.config.limitApi.AccessLimit;
import com.example.common.config.operationLog.MyLog;
import com.example.common.constants.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/thumbs")
public class ThumbsUpController {

    @Autowired
    private ThumbsUpService thumbsUpService;

    @AccessLimit(seconds = 60*60,maxCount = 60)
    @PostMapping("/up/")
    @MyLog
    public ApiResponse<Void> up(@RequestParam String name, @RequestParam(value = "article_id") Integer articleId){
        return thumbsUpService.up(name,articleId);
    }

    @AccessLimit(seconds = 60*60,maxCount = 60)
    @PostMapping("/down/")
    @MyLog
    public ApiResponse<Void> down(@RequestParam String name, @RequestParam(value = "article_id") Integer articleId){
        return thumbsUpService.down(name,articleId);
    }

    @PostMapping("/discussstatus/")
    public JSONObject discussStatus(@RequestParam String name,@RequestParam("ids") String ids){
        System.out.println(ids);
        return thumbsUpService.discussStatus(name,ids);
    }
}
