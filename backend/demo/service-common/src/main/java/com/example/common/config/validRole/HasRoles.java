package com.example.common.config.validRole;

import java.lang.annotation.*;

/**
 * @author ToTryEveryThing
 * @date 2024/1/13 22:49
 * @Description  写在类或方法上 判断是否存在role
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface HasRoles {

    String value();
}
