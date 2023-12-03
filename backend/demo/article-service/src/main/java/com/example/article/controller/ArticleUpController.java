package com.example.article.controller;


import com.example.article.service.ArticleUpService;
import com.example.common.config.operationLog.MyLog;
import com.example.common.constants.response.ApiResponse;
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
    @MyLog
    public ApiResponse<Void> up(@RequestParam String name,
                                @RequestParam Integer articleId,
                                @RequestParam Integer status){
        return articleUpService.changeUp(name, articleId, status);
    }


    @PostMapping("/user/article/upstatus/")
    public ApiResponse<Integer> status(@RequestParam Integer articleId,
                                       @RequestParam String  userName){

        return articleUpService.getStatus(articleId, userName);
    }

}
