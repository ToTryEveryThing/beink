package com.example.demo.service.impl.admin;

import com.example.demo.mapper.webMapper;
import com.example.demo.pojo.web;
import com.example.demo.service.admin.userDeleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class userDeleteImpl implements userDeleteService {

    @Autowired
    private webMapper webmapper;

    @Override
    public List<web> delete(Integer id) {
        int res = webmapper.deleteById(id);
        if(res>=1) {
            Map<String,String> map = new HashMap<>();
            List<web> list = webmapper.selectList(null);
            for(web i :list){
                i.setPassword("666");
            }
            return list;
        }
        else return null;
    }
}
