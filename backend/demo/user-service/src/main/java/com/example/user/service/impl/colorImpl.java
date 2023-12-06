package com.example.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.common.constants.response.ApiResponse;
import com.example.common.mapper.WebMapper;
import com.example.common.pojo.web;
import com.example.user.service.colorService;
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
