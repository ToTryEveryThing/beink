package com.example.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.user.service.UserListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserListController {

    @Autowired
    private UserListService userListService;


    @PostMapping("/user/admin/list/")
    public JSONObject list(@RequestParam Map<String ,String >map){
        String name = map.get("name");
        Integer page = Integer.parseInt(map.get("page"));
        return userListService.userList(name,page);
    }


}
