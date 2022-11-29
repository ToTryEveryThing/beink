package com.example.demo.controller.admin;

import com.example.demo.pojo.web;
import com.example.demo.service.admin.userListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class userListController {

    @Autowired
    private userListService userListService;

    @PostMapping("/user/admin/list/")
    public List<web> list(@RequestParam Map<String ,String >map){
        String account = map.get("account");
        return userListService.userList(account);
    }


}
