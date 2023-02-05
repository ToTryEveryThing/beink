package com.example.demo.aop.userInfo;

import com.example.demo.utils.JwtUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 睡醒继续做梦
 * @date 2023/2/5
 */
@Component
public class GlobalInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization").substring(7);
        request.getSession().setAttribute("account", JwtUtil.parseJWT(token).getSubject());
        return true;
    }
}
