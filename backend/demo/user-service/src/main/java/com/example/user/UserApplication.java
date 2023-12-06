package com.example.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;


/**
 * @author ToTryEveryThing
 * @date 2023/12/3 9:36
 * @Description
 */
@SpringBootApplication
@EnableFeignClients
@MapperScan("com.example.common.mapper")
@MapperScan("com.example.user.mapper")
@ComponentScan(basePackages  = {"com.example.common", "com.example.user"})
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class,args);
    }
}
