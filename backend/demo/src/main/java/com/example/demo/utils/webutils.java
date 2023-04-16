package com.example.demo.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/***
 * @author 睡醒继续做梦
 * @date 2023/4/15 10:10
 */
public class webutils {

    public static String renderString (HttpServletResponse response, String string){

        try{
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return null;

    }


}
