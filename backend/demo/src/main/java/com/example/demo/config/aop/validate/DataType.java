package com.example.demo.config.aop.validate;

/**
 * @author ToTryEveryThing
 * @date 2023/8/13 17:29
 */
public enum DataType {
    PHONE_NUMBER("检查手机号"), NAME_LENGTH("确认名字长度"), NOT_NULL("不要填空值");

    private final String errorMessage;

    DataType(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
