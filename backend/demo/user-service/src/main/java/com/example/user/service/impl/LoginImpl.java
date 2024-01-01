package com.example.user.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.common.constants.response.ApiResponse;
import com.example.common.mapper.WebMapper;
import com.example.common.pojo.web;
import com.example.common.utils.Code.IsCode;
import com.example.common.utils.JwtUtil;
import com.example.common.utils.redisUtil;
import com.example.user.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.example.common.constants.radis.redisConstants.*;


@Service
@RequiredArgsConstructor
public class LoginImpl implements LoginService {

    private final PasswordEncoder passwordEncoder;

    private final  redisUtil redisUtil;

    private final WebMapper webMapper;


    @Override
    public ApiResponse<Map<String, String>> getToken(String account, String password, String code) {

        Boolean f = IsCode.is(REDIS_CAPTCHA + account + ":" + code,redisUtil);
        if(!f){
            return ApiResponse.error(0,"验证码错误");
        }

        LambdaQueryWrapper<web> q = new LambdaQueryWrapper<>();
        q.eq(web::getAccount,account);
        web web = webMapper.selectOne(q);
        if(web == null){
            System.out.println("用户不存在");
            return    ApiResponse.error(0,"用户不存在");
        }
        if(!web.getEnable()){
            return    ApiResponse.error(0,"用户被锁定");
        }
        if (!passwordEncoder.matches(password, web.getPassword())) {
            return ApiResponse.error(0,"密码错误");
        }

        String jwt = JwtUtil.createJWT(web.getAccount(), web.getRole());
        Map<String,String> map = new HashMap<>();

        map.put("token",jwt);

        System.out.println("web.getEnable() = " + web.getEnable());

        redisUtil.set(REDIS_TOKEN + account, jwt, REDIS_JWT_TTL);
        redisUtil.del(REDIS_CAPTCHA + account + ":" + code);

        return ApiResponse.success(map);
    }
}
