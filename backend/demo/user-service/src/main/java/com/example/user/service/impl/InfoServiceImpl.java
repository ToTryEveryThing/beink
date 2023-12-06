package com.example.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.example.common.constants.response.ApiResponse;
import com.example.common.mapper.WebMapper;
import com.example.common.pojo.web;
import com.example.user.client.FansClient;
import com.example.user.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class InfoServiceImpl implements InfoService {

    @Autowired
    WebMapper webMapper;

    @Autowired
    private FansClient fansClient;

    @Override
    public ApiResponse<Map<String, String>> getinfo() {



//   TODO 拿到token中信息
        QueryWrapper<web> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account","admin");
        web web = webMapper.selectOne(queryWrapper);

        Map<String,String> map = new HashMap<>();
        map.put("code", "200");
        map.put("id",String.valueOf(web.getId()));
        map.put("account",web.getAccount());
        map.put("password",web.getPassword());
        map.put("backImg",web.getBackimg());
        map.put("listlist",web.getList());
        map.put("email", web.getMail());
        map.put("role",web.getRole());
        map.put("userimage",web.getUserimage());
        map.put("date", String.valueOf(web.getDate()));
        map.put("article", String.valueOf(fansClient.sendarticle(web.getId())));

        web.setLoginTime(new Date());
        webMapper.updateById(web);

        return ApiResponse.success(map);
    }
}
