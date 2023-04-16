package com.example.demo.exception.controllerException;

public class LimitException extends RuntimeException{

    private Integer code;

    public Integer getCode() {
        return code;
    }

    public LimitException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public LimitException(String message, Throwable cause, Integer code) {
        super(message, cause);
        this.code = code;
    }

}
