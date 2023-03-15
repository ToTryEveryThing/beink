package com.example.demo.service.impl.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.controller.common.Result;
import com.example.demo.mapper.ChatMapper;
import com.example.demo.pojo.Chat;
import com.example.demo.service.web.chatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    public Result select(String from, String to) {
        QueryWrapper<Chat> q = new QueryWrapper<>();
        q.eq("`from`",from).eq("`to`",to).or().eq("`from`",to).eq("`to`",from);
        List<Chat> chats = chatMapper.selectList(q);
        System.out.println(chats);
        return new Result(1,"success",chats);
    }
}
