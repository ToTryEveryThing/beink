package com.example.demo.service.impl.admin;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.controller.common.Result;
import com.example.demo.mapper.PublicMapper;
import com.example.demo.mapper.WebMapper;
import com.example.demo.pojo.Public;
import com.example.demo.service.admin.userDeleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class userDeleteImpl implements userDeleteService {

    @Autowired
    private PublicMapper publicMapper;

    @Autowired
    private WebMapper webmapper;

    @Override
    public Result delete(Integer id) {

        if(id==1)return new Result(0,"error");
        String name = webmapper.selectById(id).getAccount();
        int res = webmapper.deleteById(id);
//删除文章
        QueryWrapper<Public> publicQueryWrapper = new QueryWrapper<>();
        publicQueryWrapper.eq("name",name);
        int delete2 = publicMapper.delete(publicQueryWrapper);

        if(res + delete2 >= 1) {
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
            if((Integer)ids.get(i)==1)continue;
            list.add((Integer) ids.get(i));
        }

        int i = webmapper.deleteBatchIds(list);
        if(i==ids.size()){
            return new Result(1,"删除完成");
        }
        return new Result(0,"删除出错");

    }
}
