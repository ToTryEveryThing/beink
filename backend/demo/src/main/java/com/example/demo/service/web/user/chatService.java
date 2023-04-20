package com.example.demo.service.web.user;

import com.example.demo.controller.common.ApiResponse;
import com.example.demo.pojo.Chat;

import java.util.List;

/***
 * @author 睡醒继续做梦
 * @date 2023/3/13 15:29
 */
public interface chatService {

    public ApiResponse<List<Chat>> select(String from, String to);

}
