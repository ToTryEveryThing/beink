package com.example.demo.controller.admin;


import com.example.demo.pojo.web;
import com.example.demo.service.admin.userDeleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class userDeleteController {

    @Autowired
    private userDeleteService userDeleteService;

    @PostMapping("/user/admin/delete/")
    public List<web> delete(@RequestParam Map<String , String> map){
        int id = Integer.parseInt(map.get("id"));
        return userDeleteService.delete(id);
    }



}
