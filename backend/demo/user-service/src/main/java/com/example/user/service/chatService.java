package com.example.user.service;



import com.example.common.constants.response.ApiResponse;
import com.example.user.domain.Chat;

import java.util.List;

/***
 * @author 睡醒继续做梦
 * @date 2023/3/13 15:29
 */
public interface chatService {

    public ApiResponse<List<Chat>> select(String from, String to);

}
