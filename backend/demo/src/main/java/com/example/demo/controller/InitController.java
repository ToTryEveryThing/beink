package com.example.demo.controller;


import com.example.demo.mapper.article.ArticleMapper;
import com.example.demo.mapper.article.ArticleUpMapper;
import com.example.demo.pojo.article.article;
import com.example.demo.pojo.article.articleUp;
import lombok.extern.slf4j.Slf4j;

import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * @author 睡醒继续做梦
 * @date 2023/1/16
 */
@Slf4j
@RestController
public class InitController {


    @Autowired
    RedissonClient redissonClient;

    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    ArticleUpMapper articleUpMapper;



    @GetMapping ("/redis/")
    public String  index(){
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
        } else if (authorities.stream().anyMatch(auth -> auth.getAuthority().equals("admin"))){
            System.out.println("woc");
            return null;
        }else {
            for (GrantedAuthority i : authorities){
                System.out.println(i);
            }
            // 当前用户没有任何角色
            System.out.println("当前用户没有任何角色");
            return null;
        }
    }


    @Transactional
    @GetMapping("/jjjjj/osss/")
    public void  testt(){
        article article = new article();
        article.setId(47);
        article.setUp(400);
        int i1 = articleMapper.updateById(article);
        System.out.println("i1 = " + i1);
        int i = articleUpMapper.updateById(new articleUp(11, 12, "455", 5550));
        System.out.println("i = " + i);

//        throw  new RuntimeException("gsdg");

    }



}
