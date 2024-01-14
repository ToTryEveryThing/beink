package com.example.common.constants;

import lombok.Getter;

/**
 * @author ToTryEveryThing
 * @date 2024/1/14 11:47
 * @Description
 */
@Getter
public enum UserType {

    ADMIN("admin","管理员"),
    TEST("test","测试用户"),
    USER("user","普通用户");

    private final String type;
    private final String desc;

    UserType(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

}
