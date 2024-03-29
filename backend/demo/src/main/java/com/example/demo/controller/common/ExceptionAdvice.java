package com.example.demo.controller.common;

import com.example.demo.config.aop.operationLog.MyLog;
import com.example.demo.exception.controllerException.LimitException;
import com.example.demo.exception.controllerException.TokenException;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

/**
 * @author 睡醒继续做梦
 * @date 2023/2/2
 */
@RestControllerAdvice
public class ExceptionAdvice {

    /**
     * 限制太频繁
     */
    @MyLog
    @ExceptionHandler(LimitException.class)
    public ApiResponse<Void> Limit(LimitException e){
        System.out.println("访问频率异常");
        return ApiResponse.error(e.getCode(),e.getMessage());
    }

    /**
     * token异常
     */
    @MyLog
    @ExceptionHandler(TokenException.class)
    public ApiResponse<Void> token(TokenException e){
        System.out.println("token异常");
        return ApiResponse.error(e.getCode(), e.getMessage());
    }

    /**
     * 没有自定义的异常
     */
    @MyLog
    @ExceptionHandler(Exception.class)
    public ApiResponse<Void> doException(Exception e){
        System.out.println(e);
        System.out.println(e.getClass());
        System.out.println("没有名字的异常");
        return ApiResponse.error(ApiResponse.Status.INTERNAL_SERVER_ERROR);
    }

    /**
     * 访问方法不对
     */
    @MyLog
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ApiResponse<Void> Methods(HttpRequestMethodNotSupportedException e){
        return ApiResponse.error(ApiResponse.Status.METHOD_NOT_ALLOWED);
    }


    /**
     * 访问参数错误
     */
    @MyLog
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> BAD_REQUEST(MissingServletRequestParameterException e){
        return ApiResponse.error(ApiResponse.Status.BAD_REQUEST,e.getMessage());
    }



    /**
     * 账号被停用
     */
    @MyLog
    @ExceptionHandler(DisabledException.class)
    public ApiResponse<Void> disabled(DisabledException e){
        System.out.println("用户不可用");
        return ApiResponse.error(0,"账号被停用");
    }


    /**
     * 密码错误
     */
    @MyLog
    @ExceptionHandler(BadCredentialsException.class)
    public ApiResponse<Void> passwdWrong(BadCredentialsException e){
        System.out.println("密码错误");
        return ApiResponse.error(0, "密码错误");
    }


    /**
     * 参数校验
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ApiResponse<String> hh(ConstraintViolationException e){

        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        StringBuilder msg = new StringBuilder();

        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
            PathImpl pathImpl = (PathImpl) constraintViolation.getPropertyPath();
            String paramName = pathImpl.getLeafNode().getName();
            String message = constraintViolation.getMessage();
            if(message != null && !message.isEmpty()) msg.append("[").append(paramName).append(":").append(message).append("]");
        }

        return ApiResponse.error(ApiResponse.Status.REQUEST_PARAMETER_VALIDATION_FAILED,msg.toString());
    }



}
