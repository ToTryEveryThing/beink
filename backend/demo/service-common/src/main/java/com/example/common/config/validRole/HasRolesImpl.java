package com.example.common.config.validRole;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.common.exception.controllerException.TokenException;
import com.example.common.mapper.WebMapper;
import com.example.common.pojo.web;
import com.example.common.utils.UserContext;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author ToTryEveryThing
 * @date 2024/1/13 22:52
 * @Description
 */
@Aspect
@Component
@RequiredArgsConstructor
public class HasRolesImpl {

    private final WebMapper webMapper;

    /**
     * 注意 包括范围
     */
    @Before("execution(*  com.example.*.controller.*.*(..))")
    public void beforeValidRoles(JoinPoint joinPoint) throws  TokenException{

        // 获取目标类
        // 先判断 类上的注解
        Class<?> targetClass = joinPoint.getTarget().getClass();
        HasRoles classHasRolesAnnotation = targetClass.getAnnotation(HasRoles.class);

        if (classHasRolesAnnotation != null) {
            // 获取 HasRoles 注解的值（类级别）
            String classRoles = classHasRolesAnnotation.value();
            System.out.println("Roles from class annotation: " + classRoles);
            throw new TokenException("hh",123);
        }

        // 获取目标方法
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method targetMethod = methodSignature.getMethod();
        HasRoles methodHasRolesAnnotation = targetMethod.getAnnotation(HasRoles.class);

        // 检查是否有 HasRoles 注解在方法上
        if (methodHasRolesAnnotation != null) {
            // 获取 HasRoles 注解的值（方法级别）
            String methodRoles = methodHasRolesAnnotation.value();

            // 获取用户角色信息
            LambdaQueryWrapper<web> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(com.example.common.pojo.web::getAccount, UserContext.getUser());
            web web = webMapper.selectOne(queryWrapper);

            if (web != null) {
                // 打印角色信息
                System.out.println("Roles from method annotation: " + methodRoles);
                System.out.println("User's role from database: " + web.getRole());

                // 进行权限验证等其他逻辑...
            } else {
                System.out.println("User not found in the database.");
            }
        }
    }


}
