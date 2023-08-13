package com.example.demo.controller.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/***
 * @author 睡醒继续做梦
 * @date 2023/4/20 9:54
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {

    private int code;
    private String message;
    private T data;

    @Getter
    public enum Status {
        OK(200, "操作成功"),
        BAD_REQUEST(400, "错误的请求"),
        UNAUTHORIZED(401, "没有权限"),
        FORBIDDEN(403, "禁止访问"),
        NOT_FOUND(404, "没有找到"),
        REQUEST_PARAMETER_VALIDATION_FAILED(422,"请求参数验证失败"),
        METHOD_NOT_ALLOWED(405, "不允许使用此方法"),
        INTERNAL_SERVER_ERROR(500, "内部错误");


        private final int code;
        private final String message;

        Status(int code, String message) {
            this.code = code;
            this.message = message;
        }

    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(Status.OK.getCode(), Status.OK.getMessage(), data);
    }

    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>(Status.OK.getCode(), Status.OK.getMessage(), null);
    }

    public static <T> ApiResponse<T> error(Status status) {
        return new ApiResponse<>(status.getCode(),status.getMessage(), null);
    }

    public static <T> ApiResponse<T> error(Status status, String message) {
        return new ApiResponse<>(status.getCode(),message, null);
    }

    //    自定义
    public static <T> ApiResponse<T> error(int code, String message) {
        return new ApiResponse<>(code, message, null);
    }
}

