package com.example.demo.config.aop.captcha;

import com.example.demo.utils.Code.IsCode;
import com.example.demo.utils.redisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.example.demo.constants.radis.redisConstants.REDIS_CAPTCHA;

/**
 * @author 睡醒继续做梦
 * @date 2023/2/12
 */
@Component
public class captchaInterceptor implements HandlerInterceptor {

    @Autowired
    private redisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String code = request.getParameter("code");
        String base = request.getParameter("base64");
        System.out.print(code);
        Boolean f = new IsCode().is(REDIS_CAPTCHA + code,base,redisUtil);
        if(!f){
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("error");
            response.getWriter().flush();
            return false;
        }
        return true;
    }
}
