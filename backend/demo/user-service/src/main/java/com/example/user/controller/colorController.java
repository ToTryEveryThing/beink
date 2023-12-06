package com.example.user.controller;


import com.example.common.config.limitApi.AccessLimit;
import com.example.common.config.operationLog.MyLog;
import com.example.common.constants.response.ApiResponse;
import com.example.user.service.colorService;
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
    @MyLog
    public ApiResponse<Void> setColor(@RequestParam String account, @RequestParam Map<String,String>map){
        String color = map.get("backImg");
        String list = map.get("list");
        return colorService.setColor(account,color,list);
    }
}
