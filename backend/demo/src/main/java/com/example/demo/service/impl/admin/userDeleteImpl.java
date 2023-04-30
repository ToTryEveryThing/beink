package com.example.demo.service.impl.admin;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.controller.common.ApiResponse;
import com.example.demo.mapper.user.WebMapper;
import com.example.demo.service.admin.userDeleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class userDeleteImpl implements userDeleteService {


    @Autowired
    private WebMapper webmapper;

    @Override
    public ApiResponse<Void> delete(Integer id) {

        if(id==1)return ApiResponse.error(0,"error");
        int res = webmapper.deleteById(id);
        if(res  >= 1) {
           return ApiResponse.success();
        }
        else return ApiResponse.error(0,"error");
    }

    @Override
    public ApiResponse<Void> deleteMultiple(String s) {

        JSONObject data = JSONObject.parseObject(s);

        JSONArray ids = data.getJSONArray("ids");

        ArrayList<Integer> list=new ArrayList();

        for(int i=0;i<ids.size();i++){
            if((Integer)ids.get(i)==1)continue;
            list.add((Integer) ids.get(i));
        }

        int i = webmapper.deleteBatchIds(list);
        if(i==ids.size()){
            return ApiResponse.success();
        }
        return ApiResponse.error(0,"删除出错");

    }
}
