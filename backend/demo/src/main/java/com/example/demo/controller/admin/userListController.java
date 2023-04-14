package com.example.demo.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.config.aop.checkRole.PermissionCheck;
import com.example.demo.service.admin.userListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class userListController {

    @Autowired
    private userListService userListService;

    @PostMapping("/user/admin/list/")
    public JSONObject list(@RequestParam Map<String ,String >map){
        String name = map.get("name");
        Integer page = Integer.parseInt(map.get("page"));
        return userListService.userList(name,page);
    }


}
