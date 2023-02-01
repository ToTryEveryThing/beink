package com.example.demo.controller.admin;

import com.example.demo.service.admin.adminUtilsService;
import com.example.demo.utils.IdandName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 睡醒继续做梦
 * @date 2022/11/26
 */

@RestController
public class adminUtilsController {

    @Autowired
    private adminUtilsService adminUtilsService;

    @Autowired
    private HttpServletRequest request;

//    +
    @PostMapping("/user/admin/git/save/")
    public String save(@RequestParam Map<String , String> map) throws Exception {
        if(!new IdandName().admin(request.getHeader("Authorization"),map.get("account"))){
            return "error";
        }
        return adminUtilsService.save(map.get("markdown"));
    }

    @PostMapping("/user/admin/git/show/")
    public String show(){
        return adminUtilsService.redisShow();
    }

}
