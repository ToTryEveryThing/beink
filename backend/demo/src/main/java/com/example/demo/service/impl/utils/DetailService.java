package com.example.demo.service.impl.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.mapper.WebMapper;

import com.example.demo.pojo.web;
import com.example.demo.service.impl.utils.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DetailService implements UserDetailsService {

    @Autowired
    private WebMapper webMapper;

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        QueryWrapper<web> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account",account);
        web web = webMapper.selectOne(queryWrapper);

        if(web == null){
            throw new RuntimeException("用户不存在");
        }
//There is no PasswordEncoder mapped for the id "null" 密码没有加密 在密码前加{noop}
        return new UserDetailsImpl(web);
    }
}