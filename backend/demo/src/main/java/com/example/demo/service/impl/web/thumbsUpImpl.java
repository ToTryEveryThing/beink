package com.example.demo.service.impl.web;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.controller.common.Result;
import com.example.demo.mapper.DiscussMapper;
import com.example.demo.mapper.UpMapper;
import com.example.demo.pojo.Discuss;
import com.example.demo.pojo.ThumbsUp;
import com.example.demo.service.web.thumbsUp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class thumbsUpImpl implements thumbsUp {

    @Autowired
    private UpMapper upMapper;

    @Autowired
    private DiscussMapper discussMapper;

    @Override
    public Result up(String userName, Integer articleId) {
        int insert;
        QueryWrapper<ThumbsUp> q = new QueryWrapper<>();
        q.eq("user_name",userName);
        q.eq("article_id",articleId);
        ThumbsUp thumbsUp1 = upMapper.selectOne(q);
//        存在
        if(thumbsUp1!=null){
            Boolean f = thumbsUp1.getStatus();
            if(f) return new Result(0,"error");
            thumbsUp1.setStatus(true);
            insert = upMapper.update(thumbsUp1,q);
        }else{
            ThumbsUp thumbsUp = new ThumbsUp();
            thumbsUp.setStatus(true);
            thumbsUp.setArticleId(articleId);
            thumbsUp.setUserName(userName);
            thumbsUp.setCount(1);
            insert = upMapper.insert(thumbsUp);
        }
        Discuss discuss = discussMapper.selectById(articleId);
        discuss.setUp(discuss.getUp()+1);
        discussMapper.updateById(discuss);
        if(insert>=1)return new Result(1,"success");
        return new Result(0,"error");
    }

    @Override
    public Result down(String userName, Integer articleId) {
        QueryWrapper<ThumbsUp> q = new QueryWrapper<>();
        q.eq("user_name",userName);
        q.eq("article_id",articleId);
        ThumbsUp thumbsUp = upMapper.selectOne(q);
        if(!thumbsUp.getStatus()){
            return new Result(0,"error");
        }
        thumbsUp.setStatus(false);
        upMapper.update(thumbsUp,q);
        Discuss discuss = discussMapper.selectById(articleId);
        int sum =discuss.getUp();
        discuss.setUp(sum-1);
        discussMapper.updateById(discuss);
        return new Result(1,"success");
    }

    @Override
    public JSONObject discussStatus(String userName,String ids) {
        JSONObject data = JSONObject.parseObject(ids);
        JSONObject res = new JSONObject();
        JSONArray hh = data.getJSONArray("ids");
        System.out.println(hh.getClass());
        for(int i=0;i<hh.size();i++){
            System.out.println(hh.get(i));
            QueryWrapper<ThumbsUp> q = new QueryWrapper<>();
            Integer id = (Integer) hh.get(i);
            q.eq("article_id",id);
            ThumbsUp thumbsUp = upMapper.selectOne(q);
            if(thumbsUp==null){
                res.put(String.valueOf(id),false);
            }else
            res.put(String.valueOf(thumbsUp.getArticleId()),thumbsUp.getStatus());
        }
        return res;
    }
}
