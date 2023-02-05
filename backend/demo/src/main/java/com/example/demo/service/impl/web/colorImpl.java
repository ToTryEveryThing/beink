package com.example.demo.service.impl.web;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.demo.controller.common.Result;
import com.example.demo.mapper.WebMapper;
import com.example.demo.pojo.web;
import com.example.demo.service.web.colorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class colorImpl implements colorService {

    @Autowired
    private WebMapper webMapper;

    @Override
    public Result setColor(String account, String color, String list) {
        web web = new web();
        web.setBackimg(color);
        web.setList(list);
        UpdateWrapper<web> q = new UpdateWrapper<>();
        q.eq("account",account);
        int update = webMapper.update(web, q);
        if(update>=1)
            return new Result(1,"success");
        else
            return new Result(0,"error");
    }
}
