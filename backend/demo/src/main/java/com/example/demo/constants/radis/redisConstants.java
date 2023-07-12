package com.example.demo.constants.radis;

/***
 * @author 睡醒继续做梦
 * @date 2023/4/11 11:07
 */
public class redisConstants {
    public static final String  REDIS_ACCESS = "access:";
    public static final String  REDIS_OSS = "oss:";
    public static final String REDIS_CAPTCHA = "captcha:";
    public static final String REDIS_TOKEN = "Token:";
    public static final long REDIS_JWT_TTL = 60 * 60 * 1000L * 24 * 7;  // 有效期7天
    public static final String REDIS_ARTICLE = "article";
    public static final String REDIS_REGISTER = "register";
    public static final String REDIS_CHAT = "chat";
    public static final String REDIS_LIMIT = "limit";

    public static final String REDIS_OSS_USER = "userImage";
}
