package com.example.user.controller;


import com.example.common.config.limitApi.AccessLimit;
import com.example.common.config.operationLog.MyLog;
import com.example.common.constants.response.ApiResponse;
import com.example.common.utils.Code.CodeTypeEnum;
import com.example.common.utils.Code.EasyCaptchaService;
import com.example.common.utils.redisUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.example.common.constants.radis.redisConstants.REDIS_CAPTCHA;


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
    @ApiOperation("验证码")
    @PostMapping("/user/captcha/")
    public ApiResponse captcha(){
        Map<String, String> Base = easyCaptchaService.getCaptchaValueAndBase64(CodeTypeEnum.SPEC);
        redisTemplate.set(REDIS_CAPTCHA + Base.get("code").toLowerCase(),Base.get("base64"));
        redisTemplate.expire(REDIS_CAPTCHA + Base.get("code"), 120);
        return new ApiResponse(200,"success",Base.get("base64"));
    }
}
