package com.example.user.controller;


import com.example.common.config.limitApi.AccessLimit;
import com.example.common.config.operationLog.MyLog;
import com.example.common.constants.response.ApiResponse;
import com.example.user.service.LoginService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class LoginController  {

    @Autowired
    private LoginService loginService;

    @AccessLimit(seconds = 60*60*24, maxCount = 50)
    @PostMapping("/user/account/token/")
    @ApiOperation("登录")
    @MyLog
    public ApiResponse<Map<String, String>> getToken(@RequestParam Map<String,String>map){
        String account = map.get("account");
        String password = map.get("password");
        String code = map.get("code");
        String base64 = map.get("base64");
        return loginService.getToken(account, password, code, base64);

    }

}
