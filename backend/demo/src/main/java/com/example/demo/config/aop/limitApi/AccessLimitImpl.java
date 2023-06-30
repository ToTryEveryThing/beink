package com.example.demo.config.aop.limitApi;

import com.example.demo.exception.controllerException.LimitException;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

import static com.example.demo.constants.radis.redisConstants.REDIS_ACCESS;

@Component
@Aspect
@Log4j2
public class AccessLimitImpl {

    @Autowired
    private RedisTemplate redisTemplate;

    @Around("@annotation(accessLimit)")
    public Object around(ProceedingJoinPoint pjp, AccessLimit accessLimit) throws Throwable {
        // 获取当前请求的 IP 地址 和请求的路径
        String ip = getRequestIp();
        System.out.println(ip);
        // 获取限制时间段和最大访问次数
        int seconds = accessLimit.seconds();
        int maxCount = accessLimit.maxCount();

        // 拼接 Redis 键名
        String key = REDIS_ACCESS + ip;

        // 获取 Redis 中存储的当前访问次数
        Integer count = (Integer) redisTemplate.opsForValue().get(key);
        if (count == null) {
            // 如果没有存储，则初始化为 1
            redisTemplate.opsForValue().set(key, 1, seconds, TimeUnit.SECONDS);
        } else if (count < maxCount) {
            // 如果次数未超过最大访问次数，则加 1
            redisTemplate.opsForValue().increment(key);
        } else {
            // 如果次数超过了最大访问次数，则返回错误信息
            log.warn(key,"访问次数过多");
            throw new LimitException("服务异常,稍后再试",-1);
        }

        // 继续执行方法
        return pjp.proceed();
    }


    /**
     * 获取ip
     * @return
     */
    private String getRequestIp() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip + request.getRequestURL();
    }

}
