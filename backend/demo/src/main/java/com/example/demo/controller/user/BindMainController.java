package com.example.demo.controller.user;

import com.example.demo.config.aop.limitApi.AccessLimit;
import com.example.demo.config.aop.userInfo.UserInfo;
import com.example.demo.controller.common.ApiResponse;
import com.example.demo.service.impl.web.MailBoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/***
 * @author 睡醒继续做梦
 * @date 2023/3/1 17:14
 */

@RestController
public class BindMainController {

    @Autowired
    private MailBoxService mailBoxService;

    @AccessLimit(seconds = 120, maxCount = 2)
    @PostMapping("/user/mailbind/")
    public ApiResponse<Void> mail(@UserInfo String account , @RequestParam Map<String,String> map){
        return mailBoxService.bind(account,
                map.get("mail"),map.get("code"));
    }


}
