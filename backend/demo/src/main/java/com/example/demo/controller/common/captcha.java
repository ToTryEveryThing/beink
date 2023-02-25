package com.example.demo.controller.common;

import com.example.demo.aop.limitApi.AccessLimit;
import com.example.demo.utils.Code.CodeTypeEnum;
import com.example.demo.utils.Code.EasyCaptchaService;
import com.example.demo.utils.redisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author 睡醒继续做梦
 * @date 2023/2/11
 */
@RestController
public class captcha {

    @Autowired
    private EasyCaptchaService easyCaptchaService;

    @Autowired
    private redisUtil redisTemplate;

    @AccessLimit(seconds = 5,maxCount = 2)
    @PostMapping("/captcha/")
    public String captcha(){
        Map<String, String> Base = easyCaptchaService.getCaptchaValueAndBase64(CodeTypeEnum.SPEC);
        redisTemplate.set(Base.get("code"),Base.get("base64"));
        redisTemplate.expire(Base.get("code"), 120);
        return Base.get("base64");
    }
}
