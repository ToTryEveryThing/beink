package com.example.demo.config;

import com.example.demo.config.filter.XSSFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 睡醒继续做梦
 * @date 2023/2/16
 */

@Configuration
public class XSSConfig {

    @Bean
    public FilterRegistrationBean<XSSFilter> xssFilterRegistration() {
        FilterRegistrationBean<XSSFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new XSSFilter());
        registration.addUrlPatterns("/*");
        registration.setName("xssFilter");
        registration.setOrder(1);
        return registration;
    }
}
