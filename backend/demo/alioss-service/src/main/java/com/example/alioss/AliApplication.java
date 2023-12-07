package com.example.alioss;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author ToTryEveryThing
 * @date 2023/12/7 17:48
 * @Description
 */
@SpringBootApplication
@MapperScan("com.example.common.mapper")
@ComponentScan(basePackages  = {"com.example.common", "com.example.alioss"})
public class AliApplication {

    public static void main(String[] args) {
        SpringApplication.run(AliApplication.class,args);
    }

}
