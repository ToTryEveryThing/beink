package com.example.common.config.validRole;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.common.constants.UserType;
import com.example.common.exception.controllerException.NoPermission;
import com.example.common.exception.controllerException.TokenException;
import com.example.common.mapper.WebMapper;
import com.example.common.pojo.web;
import com.example.common.utils.UserContext;
import com.example.common.utils.verifyRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Component
@RequiredArgsConstructor
public class HasRolesImpl {

    private final WebMapper webMapper;

    /**
     * 注意 包括范围
     */
    @Before("execution(*  com.example.*.controller.*.*(..))")
    public void beforeValidRoles(JoinPoint joinPoint) throws NoPermission {

        // 获取目标类
        // 先判断 类上的注解
        Class<?> targetClass = joinPoint.getTarget().getClass();
        HasRoles classHasRolesAnnotation = targetClass.getAnnotation(HasRoles.class);

        if (classHasRolesAnnotation != null) {
            // 获取 HasRoles 注解的值（类级别）
            UserType value = classHasRolesAnnotation.value();
            verify(value);
        }

        // 获取目标方法
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method targetMethod = methodSignature.getMethod();
        HasRoles methodHasRolesAnnotation = targetMethod.getAnnotation(HasRoles.class);

        // 检查是否有 HasRoles 注解在方法上
        if (methodHasRolesAnnotation != null) {
            // 获取 HasRoles 注解的值（方法级别）
            UserType value = methodHasRolesAnnotation.value();
            verify(value);
        }
    }

    public void verify(UserType value){

        // 获取用户角色信息
        LambdaQueryWrapper<web> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(com.example.common.pojo.web::getAccount, UserContext.getUser());
        web web = webMapper.selectOne(queryWrapper);

        Boolean verify = verifyRole.verify(web.getRole(), value.getType());

        if(verify){
            log.info("访问用户role为{},需要的role为{}",web.getRole(),value.getType());
        }
        else{
            log.warn("访问用户role为{},需要的role为{}",web.getRole(),value.getType());
            throw new NoPermission();
        }

    }


}
