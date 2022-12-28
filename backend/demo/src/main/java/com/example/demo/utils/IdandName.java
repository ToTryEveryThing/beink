package com.example.demo.utils;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;


/**
 * @author 睡醒继续做梦
 * @date 2022/12/28
 */
public class IdandName {
    /**
     * 验证Token与用户是否对应
     */

    @Autowired
    private HttpServletRequest request;

    public Boolean user(String Au,String account) throws Exception {
        Au = Au.substring(7);
        Claims claims = new JwtUtil().parseJWT(Au);
        if(account.equals(claims.getSubject()))
        return true;
        return false;
    }


    public Boolean admin(String Au,String account) throws Exception {
        Au = Au.substring(7);
        Claims claims = new JwtUtil().parseJWT(Au);
        if(account.equals(claims.getSubject())&&"admin".equals(claims.getSubject()))
            return true;
        return false;
    }
}
