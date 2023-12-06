package com.example.mail.controller;


import com.example.common.constants.response.ApiResponse;
import com.example.mail.service.MailBindService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/***
 * @author 睡醒继续做梦
 * @date 2023/3/1 17:14
 */
@RestController
public class BindMainController {

    @Resource
    private MailBindService mailBindService;


    @PostMapping("/user/mail/bind/")
    public ApiResponse<Void> mail(String account , @RequestParam Map<String,String> map){
        return mailBindService.bind(account,
                map.get("mail"),map.get("code"));
    }


}
