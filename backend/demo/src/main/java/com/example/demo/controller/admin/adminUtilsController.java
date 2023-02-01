package com.example.demo.controller.admin;

import com.example.demo.aop.PermissionCheck;
import com.example.demo.service.admin.adminUtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author 睡醒继续做梦
 * @date 2022/11/26
 */

@RestController
public class adminUtilsController {

    @Autowired
    private adminUtilsService adminUtilsService;

//    +
    @PermissionCheck
    @PostMapping("/user/admin/git/save/")
    public String save(@RequestParam Map<String , String> map){
        return adminUtilsService.save(map.get("markdown"));
    }

    @PostMapping("/user/admin/git/show/")
    public String show(){
        return adminUtilsService.redisShow();
    }

}
