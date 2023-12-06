package com.example.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.user.domain.Record;
import com.example.user.mapper.RecordMapper;
import com.example.user.service.UserListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserListImpl implements UserListService {

    @Autowired
    private RecordMapper recordMapper;

    @Override
    public JSONObject userList(String name ,Integer page) {

        IPage<Record> recordIPage = new Page<>(page,10);

        QueryWrapper<Record> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("id");
        queryWrapper.like("account",name);

        List<Record> list = recordMapper.selectPage(recordIPage,queryWrapper).getRecords();
        JSONObject resp = new JSONObject();

        if(list.isEmpty()) {
            resp.put("code",0);
            return resp;
        }
        resp.put("code",200);
        resp.put("records",list);
        resp.put("count",recordMapper.selectCount(queryWrapper));
        return resp;
    }
}
