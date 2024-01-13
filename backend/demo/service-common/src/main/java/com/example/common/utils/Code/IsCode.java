package com.example.common.utils.Code;


import com.example.common.utils.redisUtil;



/**
 * @author 睡醒继续做梦
 * @date 2023/2/11
 */

public class IsCode {

    public static Boolean is(String code, redisUtil redisUtil){
        code = code.toLowerCase();
        System.out.println(code+"............");
        System.out.println("hhhhhhhhh");
        if(code==null||"".equals(code)){
            return false;
        }
        if(redisUtil.get(code)==null){
            return false;
        }
        System.out.println(redisUtil.get(code));

        return true;
    }
}
