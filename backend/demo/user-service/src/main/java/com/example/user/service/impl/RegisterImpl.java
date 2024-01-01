package com.example.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.constants.response.ApiResponse;
import com.example.common.mapper.WebMapper;
import com.example.common.pojo.web;
import com.example.common.utils.Code.IsCode;
import com.example.common.utils.JwtUtil;
import com.example.common.utils.redisUtil;
import com.example.user.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.example.common.constants.radis.redisConstants.*;


@Service
public class RegisterImpl implements RegisterService {


    @Autowired
    private WebMapper webMapper;

    @Autowired
    private redisUtil redisUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;



    @Override
    public ApiResponse<String> register(String account, String password, String code) {

        Boolean canRegister = (Boolean) redisUtil.hget(REDIS_LIMIT, REDIS_REGISTER);
        if(!canRegister){
            return ApiResponse.error(0, "目前无法注册");
        }

        Boolean f = new IsCode().is(REDIS_CAPTCHA  + account + ":" + code,redisUtil);
        if(!f){
            return ApiResponse.error(0,"验证码错误");
        }

        if(account == null){
            return ApiResponse.error(0,"用户名不能为空");
        }
        if(password.length() == 0){
            return ApiResponse.error(0,"密码不能为空");
        }

        account = account.trim();
        if(account.length()==0){
            return ApiResponse.error(0,"用户名不能为空");
        }

        if(password.length()>50){
            return ApiResponse.error(0,"密码长度不能超过50");
        }

        QueryWrapper<web> queryWrapper = new QueryWrapper<web>();
        queryWrapper.eq("account",account);
        List<web> webs =  webMapper.selectList(queryWrapper);
        if(!webs.isEmpty()){
            return ApiResponse.error(0,"用户名已存在");
        }
        String jwt =  encode(account, password);;
        redisUtil.set(REDIS_TOKEN + account, jwt, REDIS_JWT_TTL);
        redisUtil.del(REDIS_CAPTCHA + account + ":" + code);
        return ApiResponse.success(jwt);
    }

    private String  encode(String account, String password) {
        String pass = passwordEncoder.encode(password);

        String jwt = JwtUtil.createJWT(account, "user");
        web web1 = new web();
        web1.setBackimg("5");
        web1.setList("[]");
        web1.setPassword(pass);
        web1.setAccount(account);
        web1.setDate(new Date());
        webMapper.insert(web1);
        return jwt;
    }


}
