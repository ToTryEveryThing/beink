package com.example.demo.controller.common;

import com.example.demo.exception.LimitException;
import com.example.demo.exception.TokenException;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author 睡醒继续做梦
 * @date 2023/2/2
 */
//@RestControllerAdvice
public class ExceptionAdvice {

    /**
     * 限制太频繁
     * @parame
     * @return
     */
    @ExceptionHandler(LimitException.class)
    public Result Limit(LimitException e){
        System.out.println("访问频率异常");
        return new Result(e.getCode(),e.getMessage());
    }

    /**
     * token异常
     * @param e
     * @return
     */
    @ExceptionHandler(TokenException.class)
    public Result token(TokenException e){
        System.out.println("token异常");
        return new Result(e.getCode(), e.getMessage());
    }


    /**
     * 没有自定义的异常
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Result doException(Exception e){
        System.out.println("exception");
        return new Result(0,e.getMessage());
    }


}
