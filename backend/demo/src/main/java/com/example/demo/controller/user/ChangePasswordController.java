package com.example.demo.controller.user;

import com.example.demo.config.aop.userInfo.UserInfo;
import com.example.demo.controller.common.ApiResponse;
import com.example.demo.controller.common.Result;
import com.example.demo.service.impl.web.user.changePasswdServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/***
 * @author 睡醒继续做梦
 * @date 2023/4/20 9:09
 */

@RestController
public class ChangePasswordController {


    @Autowired
    changePasswdServiceImpl changePasswdService;


    @PostMapping("/user/account/changpasswd/")
    public ApiResponse<Void> changeps(@UserInfo String name,
                                      @RequestParam String Old,
                                      @RequestParam String New){

        return changePasswdService.change(name, Old, New);

    }



}
