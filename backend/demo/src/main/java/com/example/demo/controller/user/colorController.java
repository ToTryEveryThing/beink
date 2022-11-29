package com.example.demo.controller.user;

import com.example.demo.service.web.colorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class colorController {

    @Autowired
    private colorService colorService;

    @PostMapping("/user/account/color/")
    public Map<String,String> setColor(@RequestParam Map<String,String>map){
        String account = map.get("account");
        String color = map.get("backImg");
        String list = map.get("list");
        return colorService.setColor(account,color,list);

    }
}
