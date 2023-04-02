package com.example.demo.aop.checkRole;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.mapper.WebMapper;
import com.example.demo.pojo.user.web;
import com.example.demo.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author 睡醒继续做梦
 * @date 2023/2/1
 */
@Aspect
@Component
@Slf4j
public class PermissionCheckAspect {

    @Autowired
    private WebMapper webMapper;

    //切入点表达式决定了用注解方式的方法切还是针对某个路径下的所有类和方法进行切，方法必须是返回void类型
    @Pointcut("@annotation(com.example.demo.aop.checkRole.PermissionCheck)")
    private void permissionCheckCut(){};

    //定义了切面的处理逻辑。即方法上加了@PermissionCheck
    @Around("permissionCheckCut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        log.info("==========哈哈哈，进入AOP============================");
        //1.记录日志信息
        Signature signature = pjp.getSignature();
        String className = pjp.getTarget().getClass().getSimpleName();
        String methodName = signature.getName();
        log.info("className:{},methodName:{}",className,methodName);

        //2.角色权限校验
        MethodSignature methodSignature = (MethodSignature)signature;
        Method targetMethod = methodSignature.getMethod();
        if(targetMethod.isAnnotationPresent(PermissionCheck.class)){
            //获取方法上注解中表明的权限
//            PermissionCheck permission = (PermissionCheck)targetMethod.getAnnotation(PermissionCheck.class);
//            String role = permission.role();
//            log.info("当前接口请求的用户角色role:{}",role);
//            String[] roles = role.split(",");//接口允许的角色
//            List<String> list = Arrays.asList(roles);
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String authorization = request.getHeader("Authorization").substring(7);
            String userInfo =new JwtUtil().parseJWT(authorization).getSubject();
            //查询权限
            QueryWrapper<web> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("account",userInfo);
            web user = webMapper.selectOne(queryWrapper);
            userInfo = user.getRole();

            if(userInfo.equals("admin")){
                log.info("AOP权限角色校验通过，进入业务层处理！");
                //3.执行业务逻辑，放行
                return pjp.proceed();
            }else{
                //如果没有权限,抛出异常,由Spring框架捕获,跳转到错误页面
                System.out.println("权限不通过");
            }

        }
        System.out.println("权限不通过");
        return "error";
    }
}
