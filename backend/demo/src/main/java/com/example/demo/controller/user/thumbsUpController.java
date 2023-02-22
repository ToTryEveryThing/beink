package com.example.demo.controller.user;


import com.example.demo.aop.userInfo.UserInfo;
import com.example.demo.controller.common.Result;
import com.example.demo.service.impl.web.thumbsUpImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class thumbsUpController {

    @Autowired
    private thumbsUpImpl thumbsUp;

    @PostMapping("/user/up/")
    public Result up(@UserInfo String name,@RequestParam(value = "article_id") Integer articleId){
        return thumbsUp.up(name,articleId);
    }

}
