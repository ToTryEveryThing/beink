package com.example.demo.config.aop.operationLog;

import java.lang.annotation.*;

/***
 * @author 睡醒继续做梦
 * @date 2023/4/30 9:43
 */

@Target(ElementType.METHOD)//注解放置的目标位置即方法级别
@Retention(RetentionPolicy.RUNTIME)//注解在哪个阶段执行
@Documented
public @interface MyLog {
    String operType() default "";  // 操作类型

    String operDesc() default "";  // 操作说明

}
