package com.example.demo.service.impl.admin;

import com.example.demo.controller.common.Result;
import com.example.demo.mapper.webMapper;
import com.example.demo.service.admin.userDeleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class userDeleteImpl implements userDeleteService {

    @Autowired
    private webMapper webmapper;

    @Override
    public Result delete(Integer id) {
        int res = webmapper.deleteById(id);
        if(res>=1) {
           return new Result(1,"success");
        }
        else return new Result(0,"error");
    }
}
