package com.example.demo.utils.Code;

import com.example.demo.utils.redisUtil;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author 睡醒继续做梦
 * @date 2023/2/11
 */

public class IsCode {


    public Boolean is(String code,redisUtil redisUtil){
        System.out.println(code+"............");
        if(code==null||"".equals(code)){
            return false;
        }
        if(redisUtil.get(code)==null){
            return false;
        }
        if(redisUtil.get(code).equals(code)){
            redisUtil.del(code);
            return true;
        }
        return false;
    }
}
