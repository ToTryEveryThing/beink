package com.example.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.common.config.limitApi.AccessLimit;
import com.example.common.config.validRole.HasRoles;
import com.example.user.service.UserListService;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation("展示用户列表")
    public JSONObject list(@RequestParam Map<String ,String >map){
        String name = map.get("name");
        Integer page = Integer.parseInt(map.get("page"));
        return userListService.userList(name,page);
    }
}
