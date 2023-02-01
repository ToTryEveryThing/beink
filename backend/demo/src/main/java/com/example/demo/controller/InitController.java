package com.example.demo.controller;

import com.example.demo.utils.redisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author 睡醒继续做梦
 * @date 2023/1/16
 */
@RestController
public class InitController {

    @Autowired
    private redisUtil  redisUtil;

    @GetMapping ("/redis/")
    public String index(){

        redisUtil.set("hh","HH");
       return (String) redisUtil.get("hh");
    }

}
