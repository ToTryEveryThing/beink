package com.example.demo.service.impl.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.controller.common.ApiResponse;
import com.example.demo.controller.common.Result;
import com.example.demo.mapper.user.WebMapper;
import com.example.demo.pojo.user.web;
import com.example.demo.service.web.mailBox;
import com.example.demo.utils.redisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/***
 * @author 睡醒继续做梦
 * @date 2023/3/1 17:07
 */


@Service
public class MailBoxService implements mailBox {

    @Autowired
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
