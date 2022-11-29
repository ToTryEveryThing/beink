package com.example.demo.service.impl.admin;

import com.example.demo.mapper.webMapper;
import com.example.demo.pojo.web;
import com.example.demo.service.admin.userListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class userListImpl implements userListService {

    @Autowired
    private webMapper webMapper;

    @Override
    public List<web> userList(String account) {

        if(!account.equals("admin")){
            return null;
        }
        Map<String,String> map = new HashMap<>();
        List<web> list = webMapper.selectList(null);
        for(web i :list){
            i.setPassword("666");
        }
        return list;
    }
}
