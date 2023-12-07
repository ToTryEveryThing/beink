package com.example.common.exception.controllerException;

import lombok.Getter;

import javax.security.sasl.AuthenticationException;

/***
 * @author 睡醒继续做梦
 * @date 2023/4/14 8:42
 */
@Getter
public class TokenException extends AuthenticationException {

    private final Integer code;

    public TokenException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public TokenException(String  message, Throwable cause, Integer code) {
        super(message, cause);
        this.code = code;
    }
}
