package com.example.demo.aop.userInfo;

import com.example.demo.aop.captcha.captchaInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * @author 睡醒继续做梦
 * @date 2023/2/5
 */
@Configuration
public class interceptor extends WebMvcConfigurationSupport {

    @Autowired
    private GlobalInterceptor globalInterceptor;

    @Autowired
    private captchaInterceptor captchaInterceptor;


    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new UserInfoImpl());
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(globalInterceptor).addPathPatterns("/user/account/color/",
                "/user/admin/git/save/","/user/discuss/add/",
                "/user/discuss/delete/","/user/up/");
        registry.addInterceptor(captchaInterceptor).addPathPatterns("/user/account/token/","/user/account/register/");
        super.addInterceptors(registry);

    }
}
