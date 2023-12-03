package com.example.mail.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.constants.response.ApiResponse;
import com.example.common.utils.redisUtil;
import com.example.common.mapper.WebMapper;
import com.example.common.pojo.web;
import com.example.mail.service.MailBindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author ToTryEveryThing
 * @date 2023/12/3 10:28
 * @Description
 */
@Service
public class MailBindServiceImpl implements MailBindService {

    @Resource
    private redisUtil redisUtil;

    @Autowired
    private WebMapper webMapper;

    @Override
    public ApiResponse<Void> bind(String account, String mail, String code) {

        System.out.println(redisUtil.get(mail));
        System.out.println(code);
        if(redisUtil.get(mail)==null || !redisUtil.get(mail).equals(code))
            return ApiResponse.error(0,"邮箱验证码有误");

        QueryWrapper<web> q = new QueryWrapper<>();
        q.eq("account",account);
        web web = new web();
        web.setMail(mail);
        int update = webMapper.update(web, q);
        System.out.println(update);
        if(update>=1)return ApiResponse.success();
        return ApiResponse.error(0,"邮箱验证码有误");
    }
}

