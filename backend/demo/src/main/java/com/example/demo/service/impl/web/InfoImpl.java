package com.example.demo.service.impl.web;

import com.example.demo.pojo.web;
import com.example.demo.service.impl.utils.UserDetailsImpl;
import com.example.demo.service.web.InfoService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class InfoImpl implements InfoService {
    @Override
    public Map<String, String> getinfo() {

        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();

        web web = loginUser.getWeb();


        Map<String,String> map = new HashMap<>();
        map.put("message","success");
        map.put("id",String.valueOf(web.getId()));
        map.put("account",web.getAccount());
        map.put("password",web.getPassword());
        map.put("backImg",web.getBackimg());
        map.put("listlist",web.getList());
        return map;


    }
}
