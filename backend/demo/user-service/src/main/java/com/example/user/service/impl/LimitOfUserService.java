package com.example.user.service.impl;


import com.example.common.constants.response.ApiResponse;
import com.example.common.utils.redisUtil;
import com.example.user.domain.LimitOfUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.example.common.constants.radis.redisConstants.*;


/**
 * @author 睡醒继续做梦
 * @date 2023/5/11 14:05
 */
@Service
public class LimitOfUserService {

    @Autowired
    private redisUtil redisUtil;

    public ApiResponse<Void> update(LimitOfUser limitOfUser){
        redisUtil.hset(REDIS_LIMIT, REDIS_REGISTER, limitOfUser.getRegister());
        redisUtil.hset(REDIS_LIMIT, REDIS_CHAT, limitOfUser.getChat());
        return ApiResponse.success();
    }

    public ApiResponse<Map<String, Boolean>> show(){
        Map<String, Boolean> map = new HashMap<>();
        map.put("canRegister", (Boolean) redisUtil.hget(REDIS_LIMIT, REDIS_REGISTER));
        map.put("canChat", (Boolean) redisUtil.hget(REDIS_LIMIT, REDIS_CHAT));
        return ApiResponse.success(map);
    }



}
