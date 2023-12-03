package com.example.mail;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


/**
 * @author ToTryEveryThing
 * @date 2023/12/3 9:36
 * @Description
 */
@SpringBootApplication
@MapperScan("com.example.common.mapper")
@ComponentScan(basePackages  = {"com.example.common", "com.example.mail"})
public class MailApplication {
    public static void main(String[] args) {
        SpringApplication.run(MailApplication.class,args);
    }
}
