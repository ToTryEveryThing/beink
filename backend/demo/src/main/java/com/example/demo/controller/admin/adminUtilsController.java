package com.example.demo.controller.admin;

import com.example.demo.config.aop.userInfo.UserInfo;
import com.example.demo.controller.common.Result;
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

    @PostMapping("/user/admin/git/save/")
    public Result save(@UserInfo String name,@RequestParam Map<String,String> map){
        return adminUtilsService.save(map.get("markdown"),name,map.get("title"));
    }

    @PostMapping("/user/admin/git/show/")
    public Result show(String name){
        return adminUtilsService.show(name);
    }

    @PostMapping("/user/admin/git/showall/")
    public Result showall(){
        return adminUtilsService.showAll();
    }

}
