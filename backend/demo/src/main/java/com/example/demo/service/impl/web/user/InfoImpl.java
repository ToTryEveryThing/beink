package com.example.demo.service.impl.web.user;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.controller.common.ApiResponse;
import com.example.demo.mapper.user.FansMapper;
import com.example.demo.mapper.user.WebMapper;
import com.example.demo.pojo.user.Fans;
import com.example.demo.pojo.user.web;
import com.example.demo.service.web.user.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InfoImpl implements InfoService {

    @Autowired
    WebMapper webMapper;

    @Autowired
    FansServiceImpl fansService;

    @Override
    public ApiResponse<Map<String, String>> getinfo() {

        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        String username = String.valueOf(authentication.getPrincipal());


        QueryWrapper<web> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account",username);
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
        map.put("article", String.valueOf(fansService.sendArticle(web.getId())));
        return ApiResponse.success(map);
    }
}
