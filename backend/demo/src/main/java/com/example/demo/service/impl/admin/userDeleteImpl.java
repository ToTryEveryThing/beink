package com.example.demo.service.impl.admin;

import com.example.demo.mapper.webMapper;
import com.example.demo.service.admin.userDeleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class userDeleteImpl implements userDeleteService {

    @Autowired
    private webMapper webmapper;

    @Override
    public String delete(Integer id) {
        int res = webmapper.deleteById(id);
        if(res>=1) {
           return "success";
        }
        else return "error";
    }
}
