package com.example.user.controller;


import com.example.common.config.limitApi.AccessLimit;
import com.example.common.config.operationLog.MyLog;
import com.example.common.constants.response.ApiResponse;
import com.example.common.utils.Base64HashCalculator;
import com.example.common.utils.Code.CodeTypeEnum;
import com.example.common.utils.Code.EasyCaptchaService;
import com.example.common.utils.redisUtil;
import com.example.user.utils.CaptchaPool;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.example.common.constants.radis.redisConstants.REDIS_CAPTCHA;
import static com.example.common.constants.response.ApiResponse.Status.REQUEST_PARAMETER_VALIDATION_FAILED;


/**
 * @author 睡醒继续做梦
 * @date 2023/2/11
 */
@RestController
public class captcha {

    @Autowired
    private EasyCaptchaService easyCaptchaService;

    @Autowired
    private redisUtil redisUtil;


    @AccessLimit(seconds = 60*60*24,maxCount = 20)
    @ApiOperation("验证码")
    @PostMapping("/user/captcha/")
    public ApiResponse captcha(String name){
        if(name==null|| name.isEmpty())
            return ApiResponse.error(REQUEST_PARAMETER_VALIDATION_FAILED);

        Map<String, String> Base = easyCaptchaService.getCaptchaValueAndBase64(CodeTypeEnum.SPEC);
        redisUtil.set(REDIS_CAPTCHA + name + ":" + Base.get("code").toLowerCase(),Base.get("base64"),120);
        return  ApiResponse.success(Base.get("base64"));
    }
}
