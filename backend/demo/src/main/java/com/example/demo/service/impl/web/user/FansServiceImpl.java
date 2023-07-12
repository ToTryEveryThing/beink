package com.example.demo.service.impl.web.user;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.controller.common.ApiResponse;
import com.example.demo.mapper.user.FollowMapper;
import com.example.demo.pojo.Follow;
import com.example.demo.pojo.user.Fans;
import com.example.demo.service.web.user.FansService;
import com.example.demo.mapper.user.FansMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
* @author longlong
* @description 针对表【fans】的数据库操作Service实现
* @createDate 2023-07-03 13:54:55
*/
@Service
public class FansServiceImpl implements FansService{
    @Autowired
    private FansMapper fansMapper;

    @Autowired
    private FollowMapper followMapper;

    @Override
    public void addRelationship(String name, String title, Integer authorId, Integer articleId) {

        QueryWrapper<Follow> followQueryWrapper = new QueryWrapper<>();
        followQueryWrapper.eq("following_id",authorId);
        List<Follow> follows = followMapper.selectList(followQueryWrapper);
        Fans fans = new Fans(null,authorId,articleId,null,name,title,null);

        follows.forEach(s->{
            fans.setFollowerId(s.getFollowerId());
            System.out.println(fans);
            System.out.println("\n");
            fansMapper.insert(fans);
        });
    }



    List<JSONObject> sendArticle(Integer id){
        QueryWrapper<Fans> q = new QueryWrapper<>();
        q.eq("`read`",false);
        q.eq("follower_id",id);
        List<JSONObject> res = new ArrayList<>();
        List<Fans> fans = fansMapper.selectList(q);
        fans.forEach(s->{
            JSONObject temp = new JSONObject();
            temp.put("name",s.getAuthorName());
            temp.put("title",s.getArticleTitle());
            temp.put("id",s.getArticleId());
            res.add(temp);
        });
        setArticleStatus(id);
        return res;
    }

    void setArticleStatus(Integer id){
        QueryWrapper<Fans> q = new QueryWrapper<>();
        q.eq("`read`",false);
        q.eq("follower_id",id);
        List<Fans> fans = fansMapper.selectList(q);
        fans.forEach(s->{
            s.setRead(1);
            fansMapper.updateById(s);
        });

    }

}




