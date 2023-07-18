package com.example.demo.utils.Code;

import com.example.demo.utils.redisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author 睡醒继续做梦
 * @date 2023/2/11
 */

public class IsCode {

    public static Boolean is(String code,String base,redisUtil redisUtil){
        code = code.toLowerCase();
        System.out.println(code+"............");
        System.out.println("hhhhhhhhh");
        if(code==null||"".equals(code)||base==null||"".equals(base)){
            return false;
        }
        if(redisUtil.get(code)==null){
            return false;
        }
        if(redisUtil.get(code).equals(base)){
            return true;
        }
        return false;
    }
}
