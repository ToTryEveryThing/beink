package com.example.demo.controller.user;


import com.alibaba.fastjson.JSONObject;
import com.example.demo.aop.userInfo.UserInfo;
import com.example.demo.controller.common.Result;
import com.example.demo.service.impl.web.thumbsUpImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class thumbsUpController {

    @Autowired
    private thumbsUpImpl thumbsUp;

    @PostMapping("/user/up/")
    public Result up(@UserInfo String name,@RequestParam(value = "article_id") Integer articleId){
        return thumbsUp.up(name,articleId);
    }

    @PostMapping("/user/down/")
    public Result down(@UserInfo String name,@RequestParam(value = "article_id") Integer articleId){
        return thumbsUp.down(name,articleId);
    }

    @PostMapping("/user/discussstatus/")
    public JSONObject discussStatus(@UserInfo String name,@RequestParam("ids") String ids){
        System.out.println(ids);
        return thumbsUp.discussStatus(name,ids);
    }
}
