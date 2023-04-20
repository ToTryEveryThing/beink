package com.example.demo.service.impl.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.controller.common.ApiResponse;
import com.example.demo.mapper.ChatMapper;
import com.example.demo.pojo.Chat;
import com.example.demo.service.web.user.chatService;
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
