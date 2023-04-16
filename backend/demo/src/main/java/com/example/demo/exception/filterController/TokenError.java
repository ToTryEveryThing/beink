package com.example.demo.exception.filterController;

import com.example.demo.exception.controllerException.TokenException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/***
 * @author 睡醒继续做梦
 * @date 2023/4/16 10:20
 */

@RestController
public class TokenError {

    @RequestMapping("/error/token/")
    public void rethrow(HttpServletRequest request) throws Exception {
        System.out.println("收到了");
        throw new TokenException("token已过期,请重新登录", 401);
    }
}
