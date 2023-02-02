package com.example.demo.controller;

import com.example.demo.controller.common.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;


/**
 * @author 睡醒继续做梦
 * @date 2023/1/16
 */
@RestController
public class InitController {

    @GetMapping ("/redis/")
    public Result index(){
        ArrayList<String> list = new ArrayList<>();
        list.add("hello worlod");
        list.add("世界 你好");

        return new Result(100,"你好",list);
    }

}
