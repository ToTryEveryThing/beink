package com.example.article.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.article.mapper.ArticleMapper;
import com.example.article.mapper.DiscussMapper;
import com.example.article.pojo.Discuss;
import com.example.article.pojo.Article;
import com.example.article.service.DiscussService;
import com.example.common.constants.response.ApiResponse;
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

    @Autowired
    private ArticleMapper articleMapper;


    @Override
    public ApiResponse<Void> addReply(String content,
                                      Integer articleId,
                                      String userName) {

        Discuss discuss = new Discuss(content,articleId, userName, new Date());
        Article article = articleMapper.selectById(articleId);
        article.setDiscuss(article.getDiscuss()+1);
        articleMapper.updateById(article);
        int insert = discussMapper.insert(discuss);
        if(insert>=1)
            return ApiResponse.success();

        return ApiResponse.error(0, "回复失败");
    }

    @Override
    public JSONObject showReply(Integer articleId, Integer page) {
        Page<Discuss> page1 = new Page<>(page, 6);
        QueryWrapper<Discuss> q = new QueryWrapper<>();
        q.eq("article_id",articleId);
        JSONObject res = new JSONObject();
        q.orderByDesc("up");
        List<Discuss> discusses = discussMapper.selectPage(page1,q).getRecords();
        res.put("code",200);
        res.put("data",discusses);
        res.put("count",discussMapper.selectCount(q));
        return res;
    }

    @Override
    public ApiResponse<Void> delReply(String userName, Integer id) {
        QueryWrapper<Discuss> q = new QueryWrapper<>();
        q.eq("user_name",userName);
        q.eq("id",id);
        System.out.println("userName = " + userName);
        System.out.println("id = " + id);
        int res = discussMapper.delete(q);
        System.out.println("res = " + res);
        if(res>=1) return ApiResponse.success();
        return ApiResponse.error(0, "删除失败");
    }
}
