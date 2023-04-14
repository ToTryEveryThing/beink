package com.example.demo.controller;

import com.example.demo.controller.common.Result;
import com.example.demo.utils.redisUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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


    @GetMapping("/admin/test/")
    public String  hhhh(){
        log.info("访问/admin/test接口");
        System.out.println("我他妈进来了");
        return "admin";
    }


    @GetMapping("/test/test/")
    public String  testt(){
        log.info("访问/admin/test接口");
        System.out.println("我他妈进来了");
        return "test";
    }



}
