package com.example.demo.controller.user;

import com.example.demo.config.aop.limitApi.AccessLimit;
import com.example.demo.service.impl.utils.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/***
 * @author 睡醒继续做梦
 * @date 2023/3/1 16:11
 */

@RestController
public class MailController {

    @Autowired
    private MailService mailService;

    @AccessLimit(seconds = 120, maxCount = 2)
    @PostMapping("/user/mail/")
    public String mail(@RequestParam String to){
         return mailService.sendSimpleMail(to,"4656555");
    }


}
