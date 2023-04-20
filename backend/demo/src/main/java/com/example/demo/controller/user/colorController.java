package com.example.demo.controller.user;

import com.example.demo.config.aop.limitApi.AccessLimit;
import com.example.demo.config.aop.userInfo.UserInfo;
import com.example.demo.controller.common.ApiResponse;
import com.example.demo.service.web.user.colorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class colorController {

    @Autowired
    private colorService colorService;

    @AccessLimit(seconds = 10,maxCount = 3)
    @PostMapping("/user/account/color/")
    public ApiResponse<Void> setColor(@UserInfo String account, @RequestParam Map<String,String>map){
        String color = map.get("backImg");
        String list = map.get("list");
        return colorService.setColor(account,color,list);
    }
}
