package com.example.article.controller;


import com.alibaba.fastjson.JSONObject;
import com.example.article.service.DiscussService;
import com.example.common.config.limitApi.AccessLimit;
import com.example.common.config.operationLog.MyLog;
import com.example.common.constants.response.ApiResponse;
import com.example.common.utils.UserContext;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author 睡醒继续做梦
 * @date 2023/2/16
 */
@RestController
@RequestMapping("/user/discuss")
public class DiscussController {


    @Resource
    private DiscussService discussService;

    @AccessLimit(seconds = 60*60,maxCount = 50)
    @ApiOperation("发布评论")
    @PostMapping("/add/")
    @MyLog
    public ApiResponse<Void> add( @RequestParam Map<String ,String> map){
        String user = UserContext.getUser();
        String content = map.get("content");
        Integer articleId = Integer.valueOf(map.get("article_id"));
        return discussService.addReply(content,articleId,user);
    }

    @AccessLimit(seconds = 60*60,maxCount = 50)
    @PostMapping("/show/")
    @ApiOperation("展示文章评论")
    public JSONObject show(@RequestParam Map<String ,String> map){
        return discussService.showReply(Integer.valueOf(map.get("article_id")),
                Integer.valueOf(map.get("page")));
    }

    @PostMapping("/delete/")
    @ApiOperation("删除评论")
    @MyLog
    public ApiResponse<Void> del(Integer id){
        String user = UserContext.getUser();
        return discussService.delReply(user,id);
    }

}
