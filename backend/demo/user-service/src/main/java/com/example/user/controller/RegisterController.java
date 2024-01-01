package com.example.user.controller;


import com.example.common.config.limitApi.AccessLimit;
import com.example.common.config.operationLog.MyLog;
import com.example.common.constants.response.ApiResponse;
import com.example.user.service.RegisterService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @AccessLimit(seconds = 60*60*24,maxCount = 20)
    @PostMapping("/user/account/register/")
    @ApiOperation("注册")
    @MyLog
    public ApiResponse<String> register(@RequestParam Map<String,String>map){
        String account = map.get("account");
        String password = map.get("password");
        String code = map.get("code");
        return registerService.register(account, password, code);
    }
}
