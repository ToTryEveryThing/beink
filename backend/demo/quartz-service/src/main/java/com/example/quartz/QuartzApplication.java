package com.example.quartz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author ToTryEveryThing
 * @date 2023/12/8 14:51
 * @Description
 */
@SpringBootApplication
@MapperScan("com.example.common.mapper")
@ComponentScan(basePackages  = {"com.example.common", "com.example.quartz","com.example.quartz.config"})
public class QuartzApplication {
    public static void main(String[] args) {

        SpringApplication.run(QuartzApplication.class,args);

    }
}
