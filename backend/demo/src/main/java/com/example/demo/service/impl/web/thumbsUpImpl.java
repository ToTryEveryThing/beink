package com.example.demo.service.impl.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.controller.common.Result;
import com.example.demo.mapper.DiscussMapper;
import com.example.demo.mapper.UpMapper;
import com.example.demo.pojo.Discuss;
import com.example.demo.pojo.ThumbsUp;
import com.example.demo.service.web.thumbsUp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
            int res = thumbsUp1.getCount()+1;
            thumbsUp1.setCount(res);
            if(res>=5) return new Result(0,"error");
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
}
