package com.example.gateway.constants;

import lombok.Data;

/**
 * @author ToTryEveryThing
 * @date 2023/12/11 9:26
 * @Description
 */
public class exception {
    public final static String FIRST_LOGIN = "{\n" +
            "    \"code\":403,\n" +
            "    \"message\": \"登录后操作\"\n" +
            "}";

    public final static String EXPIRED_TOKEN = "{\n" +
            "    \"code\":403,\n" +
            "    \"message\": \"登录凭证过期\"\n" +
            "}";

    public final static String NO_AUTHORIZATION = "{\n" +
            "    \"code\":403,\n" +
            "    \"message\": \"权限不足\"\n" +
            "}";

    public final static String NO_USER = "{\n" +
            "    \"code\":500,\n" +
            "    \"message\": \"用户不存在\"\n" +
            "}";
    public final static String OPERATION_EXCEPTION = "{\n" +
            "    \"code\":500,\n" +
            "    \"message\": \"操作异常\"\n" +
            "}";

}
