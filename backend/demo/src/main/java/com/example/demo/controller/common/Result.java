package com.example.demo.controller.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 睡醒继续做梦
 * @date 2023/2/2
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
//    1 成功  0 失败
    Integer code;
    String msg;
    Object data;

    public Result(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public static Result OK(Integer code, String msg, Object data) {
        return new Result(code, msg, data);
    }

    public static Result FAIL(Integer code, String msg){
        return new Result(code, msg, null);
    }
}


