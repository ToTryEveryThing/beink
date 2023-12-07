package com.example.article.controller;


import com.example.article.service.ArticleUpService;
import com.example.common.config.operationLog.MyLog;
import com.example.common.constants.response.ApiResponse;
import com.example.common.utils.UserContext;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/***
 * @author 睡醒继续做梦
 * @date 2023/4/2 11:12
 */
@RestController
public class ArticleUpController {

    @Resource
    private ArticleUpService articleUpService;

    @PostMapping("/user/article/up/")
    @ApiOperation("文章点赞")
    @MyLog
    public ApiResponse<Void> up(
                                @RequestParam Integer articleId,
                                @RequestParam Integer status){
        String user = UserContext.getUser();
        return articleUpService.changeUp(user, articleId, status);
    }


    @PostMapping("/user/article/upstatus/")
    @ApiOperation("文章点赞状态")
    public ApiResponse<Integer> status(@RequestParam Integer articleId,
                                       @RequestParam String  userName){

        return articleUpService.getStatus(articleId, userName);
    }

}
