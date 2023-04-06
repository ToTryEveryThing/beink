package com.example.demo.controller;

import com.example.demo.aop.limitApi.AccessLimit;
import com.example.demo.controller.common.Result;
import com.example.demo.utils.redisUtil;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;


/**
 * @author 睡醒继续做梦
 * @date 2023/1/16
 */
@RestController
public class InitController {

    @Resource
    private redisUtil redisUtil;

    @Autowired
    RedissonClient redissonClient;


    @GetMapping ("/redis/")
    public Result index(){

        RLock key1 = redissonClient.getLock("66666");

        key1.lock();
        try{
            Integer  key = (Integer) redisUtil.get("key");
        redisUtil.set("key",key - 1);
//            redisUtil.incr("key",1);
            System.out.println(key - 1);
        }finally {
            key1.unlock();
        }
        return null;
    }

}
