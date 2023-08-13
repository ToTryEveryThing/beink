package com.example.demo.controller;



import com.example.demo.config.aop.validate.DataType;
import com.example.demo.config.aop.validate.MyValid;
import com.example.demo.mapper.article.ArticleMapper;
import com.example.demo.mapper.article.ArticleUpMapper;
import lombok.extern.slf4j.Slf4j;


import org.hibernate.validator.constraints.Email;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @author 睡醒继续做梦
 * @date 2023/1/16
 */
@Slf4j
@Validated
@RestController
public class InitController {


    @Autowired
    RedissonClient redissonClient;

    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    ArticleUpMapper articleUpMapper;

    @GetMapping("/redis/")
    public String index() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> authorities = (List<GrantedAuthority>) authentication.getAuthorities();
        if (authorities.stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_admin"))) {
            // 当前用户具有ROLE_admin角色
            System.out.println("当前用户具有ROLE_admin角色");
            return null;
        } else if (authorities.stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_user"))) {
            // 当前用户具有ROLE_user角色
            System.out.println("当前用户具有ROLE_user角色");
            return null;
        } else if (authorities.stream().anyMatch(auth -> auth.getAuthority().equals("admin"))) {
            System.out.println("woc");
            return null;
        } else {
            for (GrantedAuthority i : authorities) {
                System.out.println(i);
            }
            // 当前用户没有任何角色
            System.out.println("当前用户没有任何角色");
            return null;
        }
    }

    @PostMapping("/ggggggg")
    public String hhh(@RequestParam("phone") @MyValid(type = DataType.PHONE_NUMBER) String phone,
                      @RequestParam("name") @MyValid(type = DataType.NOT_NULL) String name,
                      @RequestParam("email") @Email String email ){
        System.out.println("name = " + name);
        System.out.println("phone = " + phone);
        System.out.println("email = " + email);
        return "success";
    }
}