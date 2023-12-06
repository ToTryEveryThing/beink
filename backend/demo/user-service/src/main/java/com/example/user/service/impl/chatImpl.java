package com.example.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.constants.response.ApiResponse;
import com.example.user.domain.Chat;
import com.example.user.mapper.ChatMapper;
import com.example.user.service.chatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/***
 * @author 睡醒继续做梦
 * @date 2023/3/13 14:59
 */

@Service
public class chatImpl implements chatService {

    @Autowired
    private ChatMapper chatMapper;


    @Override
    public ApiResponse<List<Chat>> select(String from, String to) {
        QueryWrapper<Chat> q = new QueryWrapper<>();
        q.eq("`from`",from).eq("`to`",to).or().eq("`from`",to).eq("`to`",from);
        List<Chat> chats = chatMapper.selectList(q);
        System.out.println(chats);
        return ApiResponse.success(chats);
    }
}
