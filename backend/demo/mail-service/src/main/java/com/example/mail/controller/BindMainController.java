package com.example.mail.controller;


import com.example.common.constants.response.ApiResponse;
import com.example.common.utils.UserContext;
import com.example.mail.service.MailBindService;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation("绑定邮件")
    public ApiResponse<Void> mail(@RequestParam Map<String,String> map){
        String user = UserContext.getUser();
        return mailBindService.bind(user,
                map.get("mail"),map.get("code"));
    }


}
