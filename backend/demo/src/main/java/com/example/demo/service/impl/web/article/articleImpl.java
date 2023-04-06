package com.example.demo.service.impl.web.article;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.controller.common.Result;
import com.example.demo.mapper.article.ArticleMapper;
import com.example.demo.pojo.article.article;
import com.example.demo.service.web.article.articleService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/***
 * @author 睡醒继续做梦
 * @date 2023/3/30 18:02
 */
@Service
public class articleImpl implements articleService {

    @Autowired
    RedissonClient redissonClient;


    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public Result add(String content, String name, String title) {
        int insert = articleMapper.insert(new article(0, name, title, content, 0,0 ,0, new Date(),0));
        if(insert>=1)
            return new Result(1,"success");
        return new Result(0,"error");
    }

    @Override
    public Result edit(Integer id, String name, String post, String content, String title) {
        article article = new article();
        article.setContent(content);
        article.setTitle(title);
        article.setPost(name);
        article.setId(id);
        int i = articleMapper.updateById(article);
        if(i>=1)
            return new Result(1,"success");
        return new Result(0,"error");
    }

    @Override
    public Result delete(Integer id, String name) {
        int i = articleMapper.deleteById(id);
        if(i>=1)
            return new Result(1,"success");
        return new Result(0,"error");
    }


    @Override
    public Result showbyid(Integer id) {
        RLock lock = redissonClient.getLock(String.valueOf(id));
        lock.lock();
        try{
            article article = articleMapper.selectById(id);
            article.setViews(article.getViews()+1);
            int i = articleMapper.updateById(article);
            return new Result(1,"success",article);
        }finally {
            lock.unlock();
        }
    }

    @Override
    public Result showall() {
        QueryWrapper<article> q = new QueryWrapper<>();
        q.orderByDesc("up");
        List<article> articles = articleMapper.selectList(q);
        return new Result(1, "success", articles);
    }

    @Override
    public Result showone(String post) {
        QueryWrapper<article> q = new QueryWrapper<>();
        q.eq("post",post);
        List<article> articles = articleMapper.selectList(q);
        return new Result(1,"success",articles);
    }
}
