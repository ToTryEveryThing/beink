package com.example.demo.service.impl.admin;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.controller.common.Result;
import com.example.demo.mapper.WebMapper;
import com.example.demo.pojo.web;
import com.example.demo.service.admin.userDeleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class userDeleteImpl implements userDeleteService {

    @Autowired
    private WebMapper webmapper;

    @Override
    public Result delete(Integer id) {
        int res = webmapper.deleteById(id);
        if(res>=1) {
           return new Result(1,"success");
        }
        else return new Result(0,"error");
    }

    @Override
    public Result deleteMultiple(String s) {

        JSONObject data = JSONObject.parseObject(s);

        JSONArray ids = data.getJSONArray("ids");

        ArrayList<Integer> list=new ArrayList();

        for(int i=0;i<ids.size();i++){
            list.add((Integer) ids.get(i));
        }

        int i = webmapper.deleteBatchIds(list);
        if(i==ids.size()){
            return new Result(1,"删除完成");
        }
        return new Result(0,"删除出错");


    }
}
