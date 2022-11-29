package com.example.demo.service.impl.web;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.demo.mapper.webMapper;
import com.example.demo.pojo.web;
import com.example.demo.service.web.colorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class colorImpl implements colorService {

    @Autowired
    private webMapper webMapper;

    @Override
    public Map<String, String> setColor(String account,String color,String list) {
        web web = new web();
        web.setBackimg(color);
        web.setList(list);
        UpdateWrapper<web> q = new UpdateWrapper<>();
        q.eq("account",account);
        webMapper.update(web,q);

        Map<String,String> map = new HashMap<>();
        map.put("message","success");
        return map;
    }
}
