package com.example.demo.service.impl.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.controller.common.Result;
import com.example.demo.mapper.WebMapper;
import com.example.demo.pojo.web;
import com.example.demo.service.web.RegisterService;
import com.example.demo.utils.Code.IsCode;
import com.example.demo.utils.redisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RegisterImpl implements RegisterService {

    @Autowired
    private WebMapper webMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private redisUtil redisUtil;


    @Override
    public Result register(String account, String password, String code) {

        if(!new IsCode().is(code,redisUtil)){
            return new Result(0,"验证码错误");
        }

        if(account == null){
            return new Result(0,"用户名不能为空");
        }
        if(password.length() == 0){
            return new Result(0,"密码不能为空");
        }

        account = account.trim();
        if(account.length()==0){
            return new Result(0,"用户名不能为空");
        }

        if(password.length()>50){
            return new Result(0,"密码长度不能超过50");
        }

        QueryWrapper<web> queryWrapper = new QueryWrapper<web>();
         queryWrapper.eq("account",account);
         List<web> webs =  webMapper.selectList(queryWrapper);
         if(!webs.isEmpty()){
             return new Result(0,"用户名已存在");
         }

        String pass = passwordEncoder.encode(password);

         web web1 = new web();
         web1.setBackimg("6");
         web1.setList("[]");
         web1.setPassword(pass);
         web1.setAccount(account);
         web1.setDate(new Date());
         webMapper.insert(web1);
        System.out.println(new Date());
        return new Result(1,"success");
    }
}
