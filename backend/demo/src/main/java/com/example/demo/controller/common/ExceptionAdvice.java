package com.example.demo.controller.common;

import com.example.demo.config.aop.operationLog.MyLog;
import com.example.demo.exception.controllerException.LimitException;
import com.example.demo.exception.controllerException.TokenException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.security.sasl.AuthenticationException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author 睡醒继续做梦
 * @date 2023/2/2
 */
@RestControllerAdvice
public class ExceptionAdvice {

    /**
     * 限制太频繁
     * @parame
     * @return
     */
    @MyLog
    @ExceptionHandler(LimitException.class)
    public ApiResponse<Void> Limit(LimitException e){
        System.out.println("访问频率异常");
        return ApiResponse.error(e.getCode(),e.getMessage());
    }

    /**
     * token异常
     * @param e
     * @return
     */
    @MyLog
    @ExceptionHandler(TokenException.class)
    public ApiResponse<Void> token(TokenException e){
        System.out.println("token异常");
        return ApiResponse.error(e.getCode(), e.getMessage());
    }

    /**
     * 没有自定义的异常
     * @return
     */
    @MyLog
    @ExceptionHandler(Exception.class)
    public ApiResponse<Void> doException(Exception e){
        System.out.println(e);
        return ApiResponse.error(0,e.getMessage());
    }

    /**
     * 账号被停用
     * @param e
     * @return
     */
    @MyLog
    @ExceptionHandler(DisabledException.class)
    public ApiResponse<Void> disabled(DisabledException e){
        System.out.println("用户不可用");
        return ApiResponse.error(0,"账号被停用");
    }


    /**
     * 密码错误
     * @param e
     * @return
     */
    @MyLog
    @ExceptionHandler(BadCredentialsException.class)
    public ApiResponse<Void> passwdWrong(BadCredentialsException e){
        System.out.println("密码错误");
        return ApiResponse.error(0, "密码错误");
    }



}
