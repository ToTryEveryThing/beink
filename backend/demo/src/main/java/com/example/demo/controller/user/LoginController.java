package com.example.demo.controller.user;

import com.example.demo.controller.common.Result;
import com.example.demo.service.web.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class LoginController  {

    @Autowired
    private LoginService loginService;


    @PostMapping("/user/account/token/")
    public Result getToken(@RequestParam Map<String,String>map){

        String account = map.get("account");
        String password = map.get("password");
        return loginService.getToken(account,password);

    }

}
