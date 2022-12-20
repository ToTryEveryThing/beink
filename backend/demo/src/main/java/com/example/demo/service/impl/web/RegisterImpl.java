package com.example.demo.service.impl.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.mapper.webMapper;
import com.example.demo.pojo.web;
import com.example.demo.service.web.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RegisterImpl implements RegisterService {

    @Autowired
    private webMapper webMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public Map<String, String> register(String account, String password) {

        Map<String,String> map = new HashMap<>();

        if(account == null){
            map.put("message","用户名不能为空");
            return map;
        }
        if(password.length() == 0){
            map.put("message","密码不能为空");
            return map;
        }

        account = account.trim();
        if(account.length()==0){
            map.put("message","用户名不能为空");
            return map;
        }

        if(password.length()>50){
            map.put("message","密码长度不能超过50");
            return map;
        }

        QueryWrapper<web> queryWrapper = new QueryWrapper<web>();
         queryWrapper.eq("account",account);
         List<web> webs =  webMapper.selectList(queryWrapper);
         if(!webs.isEmpty()){
             map.put("message","用户名已存在");
             return map;
         }

        String pass = passwordEncoder.encode(password);

         web web1 = new web();
         web1.setBackimg("6");
         web1.setList("[]");
         web1.setPassword(pass);
         web1.setAccount(account);
         web1.setDate(new Date());
         webMapper.insert(web1);
        System.out.println(new Date());
        map.put("message","success");
        return map;
    }
}
