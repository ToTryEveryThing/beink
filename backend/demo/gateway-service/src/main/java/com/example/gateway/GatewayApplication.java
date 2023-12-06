package com.example.gateway;


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
@ComponentScan(basePackages  = {"com.example.common", "com.example.gateway"})
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class,args);
    }
}
