package com.example.demo.exception.filterController;

import com.alibaba.fastjson.JSON;
import com.example.demo.controller.common.Result;
import com.example.demo.utils.webutils;
import org.apache.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/***
 * @author 睡醒继续做梦
 * @date 2023/4/15 10:08
 * @describe  认证失败处理器
 */

@Component
public class AuthenticationEntryImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        Result result = new Result(HttpStatus.SC_UNAUTHORIZED, "认证失败，请重新登录" );
        String jsonString = JSON.toJSONString(result);
        //        处理异常
        webutils.renderString(httpServletResponse, jsonString);
    }
}
