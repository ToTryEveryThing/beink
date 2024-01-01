package com.example.article;

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
@MapperScan("com.example.article.mapper")
@ComponentScan(basePackages  = {"com.example.common", "com.example.article"})
public class ArticleApplication {
    public static void main(String[] args) {
        SpringApplication.run(ArticleApplication.class,args);
    }
}
