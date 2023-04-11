package com.example.demo.config.aop.userInfo;

import java.lang.annotation.*;

/**
 * @author 睡醒继续做梦
 * @date 2023/2/5
 */
@Target({ElementType.PARAMETER})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface UserInfo {
}
