package com.example.demo.controller.common;

import com.example.demo.exception.LimitException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 睡醒继续做梦
 * @date 2023/2/2
 */
//@RestControllerAdvice
public class ExceptionAdvice {

    /**
     * 限制太频繁
     * @param e
     * @return
     */
    @ExceptionHandler(LimitException.class)
    public Result Limit(LimitException e){
        System.out.println("exception");
        return new Result(e.getCode(),e.getMessage());
    }

    /**
     * 没有自定义的异常
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Result doException(){
        System.out.println("exception");
        return new Result(0,"异常");
    }
}
