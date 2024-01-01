package com.example.user.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author ToTryEveryThing
 * @date 2024/1/1 9:57
 * @Description 验证码info
 */
class CaptchaInfo {

    private boolean used;

    private Map<String, String> info;

    public CaptchaInfo(Map<String, String> info) {
        this.used = false;
        this.info = info;
    }

    public Map<String, String> getInfo() {
        return info;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

}

