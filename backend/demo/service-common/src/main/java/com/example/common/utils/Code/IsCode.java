package com.example.common.utils.Code;


import com.example.common.utils.redisUtil;



/**
 * @author 睡醒继续做梦
 * @date 2023/2/11
 */

public class IsCode {

    public static Boolean is(String code, String base, redisUtil redisUtil){
        code = code.toLowerCase();
        System.out.println(code+"............");
        System.out.println("hhhhhhhhh");
        if(code==null||"".equals(code)||base==null||"".equals(base)){
            return false;
        }
        if(redisUtil.get(code)==null){
            return false;
        }
        System.out.println(redisUtil.get(code));
        if(redisUtil.get(code).equals(base) || redisUtil.get(code)== base){
            return true;
        }
        return false;
    }
}
