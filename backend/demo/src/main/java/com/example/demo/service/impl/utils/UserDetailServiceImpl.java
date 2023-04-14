package com.example.demo.service.impl.utils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.demo.mapper.user.WebMapper;
import com.example.demo.pojo.user.web;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;


/**
 *
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private WebMapper webMapper;


    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        LambdaQueryWrapper<web> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(web::getAccount,account);
        web  web = webMapper.selectOne(queryWrapper);
        if(web == null){
            throw new RuntimeException("用户不存在");
        }

        ArrayList<String> list = new ArrayList<>();
        list.add(web.getRole());

        return new LoginUser(web, list);
    }

}