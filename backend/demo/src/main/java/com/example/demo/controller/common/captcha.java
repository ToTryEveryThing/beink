package com.example.demo.controller.common;

import com.example.demo.config.aop.limitApi.AccessLimit;
import com.example.demo.config.aop.operationLog.MyLog;
import com.example.demo.utils.Code.CodeTypeEnum;
import com.example.demo.utils.Code.EasyCaptchaService;
import com.example.demo.utils.redisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.example.demo.constants.radis.redisConstants.REDIS_CAPTCHA;

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

    @AccessLimit(seconds = 60*60*24,maxCount = 20)
    @MyLog
    @PostMapping("/captcha/")
    public Result captcha(){
        Map<String, String> Base = easyCaptchaService.getCaptchaValueAndBase64(CodeTypeEnum.SPEC);
        redisTemplate.set(REDIS_CAPTCHA + Base.get("code"),Base.get("base64"));
        redisTemplate.expire(REDIS_CAPTCHA + Base.get("code"), 120);
        return new Result(200,"success",Base.get("base64"));
    }
}
