package com.example.demo.controller.user;

import com.example.demo.service.web.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @PostMapping("/user/account/register/")
    public Map<String,String > register(@RequestParam Map<String,String>map){
        String account = map.get("account");
        String password = map.get("password");
        return registerService.register(account,password);
    }
}
