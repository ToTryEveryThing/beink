package com.example.demo.controller.user;

import com.example.demo.config.aop.limitApi.AccessLimit;
import com.example.demo.config.aop.operationLog.MyLog;
import com.example.demo.controller.common.ApiResponse;
import com.example.demo.service.web.user.RegisterService;
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
    @MyLog
    public ApiResponse<String> register(@RequestParam Map<String,String>map){
        String account = map.get("account");
        String password = map.get("password");
        String code = map.get("code");
        return registerService.register(account, password, code);
    }
}
