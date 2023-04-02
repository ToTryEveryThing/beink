package com.example.demo.controller.user.article;

import com.example.demo.aop.userInfo.UserInfo;
import com.example.demo.controller.common.Result;
import com.example.demo.service.web.article.articleUpService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/***
 * @author 睡醒继续做梦
 * @date 2023/4/2 11:12
 */
@RestController
public class articleUpController {

    @Resource
    private articleUpService articleUpService;

    @PostMapping("/user/article/up/")
    public Result  up(@UserInfo String name,
                      @RequestParam Integer articleId,
                      @RequestParam Integer status){
        return articleUpService.changeUp(name, articleId, status);
    }


    @PostMapping("/user/article/upstatus/")
    public Result status(@RequestParam Integer articleId,
                          @RequestParam String  userName){

        return articleUpService.getStatus(articleId, userName);
    }

}
