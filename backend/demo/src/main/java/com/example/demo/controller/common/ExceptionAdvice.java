package com.example.demo.controller.common;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 睡醒继续做梦
 * @date 2023/2/2
 */
//@RestControllerAdvice
public class ExceptionAdvice {

//    @ExceptionHandler(Exception.class)
    public Result doException(){
        System.out.println("exception");
        return new Result(0,"error");
    }
}