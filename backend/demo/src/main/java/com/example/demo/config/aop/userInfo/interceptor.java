package com.example.demo.config.aop.userInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

import static com.example.demo.constants.controller.controller.CAPTCHA;
import static com.example.demo.constants.controller.controller.USER_INFO;

/**
 * @author 睡醒继续做梦
 * @date 2023/2/5
 */
@Configuration
public class interceptor extends WebMvcConfigurationSupport {


    @Autowired
    private GlobalInterceptor globalInterceptor;



    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new UserInfoImpl());
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(globalInterceptor).addPathPatterns(USER_INFO);
        super.addInterceptors(registry);

    }
}
