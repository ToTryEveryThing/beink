package com.example.demo.controller.user;

import com.example.demo.aop.userInfo.UserInfo;
import com.example.demo.controller.common.Result;
import com.example.demo.service.impl.web.chatImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/***
 * @author 睡醒继续做梦
 * @date 2023/3/13 15:28
 */
@RestController
public class chatController {

    @Autowired
    private chatImpl chat;

    @PostMapping("/user/chat/content/")
    public Result select(@UserInfo String name1, @RequestParam("to") String name2){
        System.out.println(name1 + "   " + name2);
        return chat.select(name1,name2);
    }


}
