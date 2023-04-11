package com.example.demo.config.aop.checkRole;

import java.lang.annotation.*;

/**
 * @author 睡醒继续做梦
 * @date 2023/2/1
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PermissionCheck {
    //自定义角色值，如果是多个角色，用逗号分割。
    public String role() default "admin";
}

