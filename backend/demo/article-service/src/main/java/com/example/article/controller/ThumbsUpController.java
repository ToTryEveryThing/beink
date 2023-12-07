package com.example.article.controller;


import com.alibaba.fastjson.JSONObject;
import com.example.article.service.ThumbsUpService;
import com.example.common.config.limitApi.AccessLimit;
import com.example.common.config.operationLog.MyLog;
import com.example.common.constants.response.ApiResponse;
import com.example.common.utils.UserContext;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation("评论点赞")
    @MyLog
    public ApiResponse<Void> up(@RequestParam(value = "article_id") Integer articleId){
        String user = UserContext.getUser();
        return thumbsUpService.up(user,articleId);
    }

    @AccessLimit(seconds = 60*60,maxCount = 60)
    @PostMapping("/down/")
    @ApiOperation("评论不点赞")
    @MyLog
    public ApiResponse<Void> down( @RequestParam(value = "article_id") Integer articleId){
        String user = UserContext.getUser();
        return thumbsUpService.down(user,articleId);
    }

    @PostMapping("/discussstatus/")
    @ApiOperation("取消点赞或反对")
    public JSONObject discussStatus(@RequestParam("ids") String ids){
        String user = UserContext.getUser();
        System.out.println(ids);
        return thumbsUpService.discussStatus(user,ids);
    }
}
