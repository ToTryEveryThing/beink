package com.example.user.controller;


import com.example.common.constants.response.ApiResponse;
import com.example.common.utils.UserContext;
import com.example.user.domain.Chat;
import com.example.user.service.impl.chatImpl;
import io.swagger.annotations.ApiOperation;
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
@ApiOperation("发送验证码")
public class chatController {

    @Autowired
    private chatImpl chat;

    @PostMapping("/user/chat/content/")
    @ApiOperation("聊天历史")
    public ApiResponse<List<Chat>> select(@RequestParam("to") String name2){
        String user = UserContext.getUser();
        System.out.println(user + "   " + name2);
        return chat.select(user,name2);
    }


}
