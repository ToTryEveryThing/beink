package com.example.user.utils;

import com.example.common.utils.Code.CodeTypeEnum;
import com.example.common.utils.Code.EasyCaptchaService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author ToTryEveryThing
 * @date 2024/1/1 9:57
 * @Description 验证码池子
 */

@Component
public class CaptchaPool {
    private List<CaptchaInfo> captchaPool;

    @Autowired
    RedissonClient redissonClient;

    @Autowired
     private EasyCaptchaService easyCaptchaService;

    public CaptchaPool() {
        captchaPool = new ArrayList<>();
    }

    private Map<String, String> generateCaptcha() {

        RLock lock = redissonClient.getLock("captcha");
        lock.lock();
        try{
            return easyCaptchaService.getCaptchaValueAndBase64(CodeTypeEnum.SPEC);
        }finally {
            lock.unlock();
        }

    }

    public Map<String, String> getAvailableCaptcha() {
        for (CaptchaInfo captchaInfo : captchaPool) {
            if (!captchaInfo.isUsed()) {
                captchaInfo.setUsed(true);
                return captchaInfo.getInfo();
            }
        }

        // 如果池子中的验证码不够了，添加一个新的验证码
        Map<String, String> stringStringMap = generateCaptcha();
        captchaPool.add(new CaptchaInfo(stringStringMap));
        System.out.println(captchaPool.size());
        return stringStringMap;
    }

    public void releaseCaptcha(Map<String, String> captchaCode) {
        for (CaptchaInfo captchaInfo : captchaPool) {
            if (captchaInfo.getInfo().equals(captchaCode)) {
                captchaInfo.setUsed(false);
                break;
            }
        }
    }
}
