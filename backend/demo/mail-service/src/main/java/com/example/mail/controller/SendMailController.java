package com.example.mail.controller;



import com.example.common.config.limitApi.AccessLimit;
import com.example.common.config.operationLog.MyLog;
import com.example.common.constants.response.ApiResponse;
import com.example.mail.service.impl.SendMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/***
 * @author 睡醒继续做梦
 * @date 2023/3/1 16:11
 */

@RestController
public class SendMailController {

    @Resource
    private SendMailService sendMailService;

    @MyLog
    @AccessLimit(seconds = 10, maxCount = 2)
    @PostMapping("/user/mail/send/")
    public ApiResponse mail(@RequestParam String to){
         return sendMailService.sendSimpleMail(to,"4656555");
    }

    @GetMapping("/hh")
    public String hh(){
        System.out.println("con");
        return "hh";
    }

}
