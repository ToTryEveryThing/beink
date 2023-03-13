package com.example.demo.controller;

import com.example.demo.aop.limitApi.AccessLimit;
import com.example.demo.controller.common.Result;
import com.example.demo.mapper.ChatMapper;
import com.example.demo.mapper.WebMapper;
import com.example.demo.service.impl.web.chatImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;


/**
 * @author 睡醒继续做梦
 * @date 2023/1/16
 */
@RestController
public class InitController {



    @AccessLimit(seconds = 10,maxCount = 2)
    @GetMapping ("/redis/")
    public Result index(){
        ArrayList<String> list = new ArrayList<>();
        list.add("hello worlod");
        list.add("世界 你好");
        int i = 1;
        System.out.println(i++);
        return new Result(100,"你好",list);
    }

}
