package com.example.demo.controller.admin;


import com.example.demo.aop.PermissionCheck;
import com.example.demo.controller.common.Result;
import com.example.demo.pojo.web;
import com.example.demo.service.admin.userDeleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@RestController
public class userDeleteController {

    @Autowired
    private userDeleteService userDeleteService;
//+
    @PostMapping("/user/admin/delete/")
    @PermissionCheck
    public Result delete(@RequestParam Map<String , String> map) {
        int id = Integer.parseInt(map.get("id"));
        return userDeleteService.delete(id);
    }

    @PostMapping("/user/admin/multiple/")
    @PermissionCheck
    public Result DeleteMultiple(@RequestParam String ids){
        System.out.println(ids);
        return userDeleteService.deleteMultiple(ids);
    }



}
