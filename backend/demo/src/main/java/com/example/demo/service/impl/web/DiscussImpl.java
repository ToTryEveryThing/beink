package com.example.demo.service.impl.web;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.controller.common.Result;
import com.example.demo.mapper.DiscussMapper;
import com.example.demo.pojo.Discuss;
import com.example.demo.service.web.DiscussService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author 睡醒继续做梦
 * @date 2023/2/16
 */
@Service
public class DiscussImpl implements DiscussService {

    @Autowired
    private DiscussMapper discussMapper;


    @Override
    public Result addReply(String content,
                           String postName,
                           String postIndex,
                           String userName) {

        Discuss discuss = new Discuss(content,postName,postIndex,userName,new Date());

        int insert = discussMapper.insert(discuss);
        if(insert>=1)
            return new Result(1,"success");

        return new Result(0,"error");
    }

    @Override
    public JSONObject showReply(String postName, String postIndex, Integer page) {
        Page<Discuss> page1 = new Page<>(page, 6);
        QueryWrapper<Discuss> q = new QueryWrapper<>();
        JSONObject res = new JSONObject();
        q.eq("post_name",postName);
        q.eq("post_index",postIndex);
        q.orderByDesc("up");
        List<Discuss> discusses = discussMapper.selectPage(page1,q).getRecords();
        res.put("code",1);
        res.put("data",discusses);
        res.put("count",discussMapper.selectCount(q));
        return res;
    }

    @Override
    public Result delReply(String userName, Integer id) {

        QueryWrapper<Discuss> q = new QueryWrapper<>();
        q.eq("user_name",userName);
        q.eq("id",id);
        int res = discussMapper.delete(q);
        if(res>=1) return new Result(1,"success");
        return new Result(0,"error");


    }
}