package com.example.demo.service.impl.web.user;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.demo.controller.common.ApiResponse;
import com.example.demo.mapper.user.WebMapper;
import com.example.demo.pojo.user.web;
import com.example.demo.service.web.user.colorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class colorImpl implements colorService {

    @Autowired
    private WebMapper webMapper;

    @Override
    public ApiResponse<Void> setColor(String account, String color, String list) {
        web web = new web();
        web.setBackimg(color);
        web.setList(list);
        UpdateWrapper<web> q = new UpdateWrapper<>();
        q.eq("account",account);
        int update = webMapper.update(web, q);
        if(update>=1)
            return ApiResponse.success();
        else
            return ApiResponse.error(0,"error");
    }
}
