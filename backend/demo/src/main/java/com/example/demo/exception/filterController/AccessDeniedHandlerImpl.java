package com.example.demo.exception.filterController;

import com.alibaba.fastjson.JSON;
import com.example.demo.controller.common.Result;
import com.example.demo.utils.webutils;
import org.apache.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/***
 * @author 睡醒继续做梦
 * @date 2023/4/15 10:18
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        Result result = new Result(HttpStatus.SC_FORBIDDEN, "权限不足" );
        String jsonString = JSON.toJSONString(result);
        //        处理异常
        webutils.renderString(httpServletResponse, jsonString);
    }
}
