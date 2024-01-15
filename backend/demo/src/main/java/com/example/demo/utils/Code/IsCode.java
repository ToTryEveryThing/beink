package com.example.demo.utils.Code;

import com.example.demo.utils.redisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author 睡醒继续做梦
 * @date 2023/2/11
 */

public class IsCode {

    public static Boolean is(String code, redisUtil redisUtil){
        code = code.toLowerCase();
        if(redisUtil.hasKey(code)){
            System.out.println("有吗" + code);
            redisUtil.del(code);
            return true;
        }
        return false;
    }
}
