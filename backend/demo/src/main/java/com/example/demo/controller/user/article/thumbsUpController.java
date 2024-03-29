package com.example.demo.controller.user.article;


import com.alibaba.fastjson.JSONObject;
import com.example.demo.config.aop.limitApi.AccessLimit;
import com.example.demo.config.aop.operationLog.MyLog;
import com.example.demo.config.aop.userInfo.UserInfo;
import com.example.demo.controller.common.ApiResponse;
import com.example.demo.service.impl.web.article.thumbsUpImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class thumbsUpController {

    @Autowired
    private thumbsUpImpl thumbsUp;

    @AccessLimit(seconds = 60*60,maxCount = 60)
    @PostMapping("/user/up/")
    @MyLog
    public ApiResponse<Void> up(@UserInfo String name, @RequestParam(value = "article_id") Integer articleId){
        return thumbsUp.up(name,articleId);
    }

    @AccessLimit(seconds = 60*60,maxCount = 60)
    @PostMapping("/user/down/")
    @MyLog
    public ApiResponse<Void> down(@UserInfo String name, @RequestParam(value = "article_id") Integer articleId){
        return thumbsUp.down(name,articleId);
    }

    @PostMapping("/user/discussstatus/")
    public JSONObject discussStatus(@UserInfo String name,@RequestParam("ids") String ids){
        System.out.println(ids);
        return thumbsUp.discussStatus(name,ids);
    }
}
