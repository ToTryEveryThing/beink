package com.example.user.controller;


import com.example.common.constants.response.ApiResponse;
import com.example.user.domain.Chat;
import com.example.user.service.impl.chatImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/***
 * @author 睡醒继续做梦
 * @date 2023/3/13 15:28
 */
@RestController
public class chatController {

    @Autowired
    private chatImpl chat;

    @PostMapping("/user/chat/content/")
    public ApiResponse<List<Chat>> select(@RequestParam("name") String name1, @RequestParam("to") String name2){
        System.out.println(name1 + "   " + name2);
        return chat.select(name1,name2);
    }


}
