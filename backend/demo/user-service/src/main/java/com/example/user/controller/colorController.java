package com.example.user.controller;


import com.example.common.config.limitApi.AccessLimit;
import com.example.common.config.operationLog.MyLog;
import com.example.common.constants.response.ApiResponse;
import com.example.common.utils.UserContext;
import com.example.user.service.colorService;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation("用户背景颜色")
    @MyLog
    public ApiResponse<Void> setColor(@RequestParam Map<String,String>map){
        String user = UserContext.getUser();
        String color = map.get("backImg");
        String list = map.get("list");
        return colorService.setColor(user,color,list);
    }
}
