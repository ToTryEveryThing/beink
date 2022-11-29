package com.example.demo.service.impl.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.mapper.webMapper;
import com.example.demo.pojo.web;
import com.example.demo.service.admin.adminUtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 睡醒继续做梦
 * @date 2022/11/26
 */
@Service
public class adminUtilsImpl implements adminUtilsService {


    @Autowired
    private webMapper webMapper;

    @Override
    public String save(String markdown) {
        QueryWrapper<web> q = new QueryWrapper<>();
        q.eq("account","admin");
        web web = new web();
        web.setGit(markdown);
        int update = webMapper.update(web, q);
        if(update>=1)return "success";
        else return "error";
    }

    @Override
    public String show() {
        web web = webMapper.selectById(1);
        return web.getGit();
    }
}
