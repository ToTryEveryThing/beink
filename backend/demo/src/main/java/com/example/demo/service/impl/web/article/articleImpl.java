package com.example.demo.service.impl.web.article;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.controller.common.ApiResponse;
import com.example.demo.mapper.article.ArticleMapper;
import com.example.demo.mapper.article.ArticleUpMapper;
import com.example.demo.mapper.article.DiscussMapper;
import com.example.demo.mapper.article.UpMapper;
import com.example.demo.pojo.article.Discuss;
import com.example.demo.pojo.article.article;
import com.example.demo.pojo.article.articleUp;
import com.example.demo.service.web.article.articleService;
import com.example.demo.utils.redisUtil;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static com.example.demo.constants.radis.redisConstants.REDIS_ARTICLE;

/***
 * @author 睡醒继续做梦
 * @date 2023/3/30 18:02
 */
@Service
public class articleImpl implements articleService {

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    redisUtil redisUtil;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ArticleUpMapper articleUpMapper;

    @Autowired
    private DiscussMapper discussMapper;


    @Override
    public ApiResponse<Void> add(String content, String name, String title) {
        int insert = articleMapper.insert(new article(0, name, title, content, 0,0 ,0, new Date(),true,0));
        if(insert>=1)
            return ApiResponse.success();
        return  ApiResponse.error(0,"添加失败");
    }

    @Override
    public ApiResponse<Void> edit(Integer id, String name, String post, String content, String title, Boolean show) {
        article article = new article();
        article.setContent(content);
        article.setTitle(title);
        article.setPost(name);
        article.setId(id);
        article.setIsshow(show);
        int i = articleMapper.updateById(article);
        if(i>=1){
            redisUtil.hset(REDIS_ARTICLE, String.valueOf(id), articleMapper.selectById(id));
            return ApiResponse.success();
        }

        return ApiResponse.error(0,"编辑失败");
    }


    @Override
    @Transactional
    public ApiResponse<Void> delete(Integer id, String name) {

        //删除点赞
        LambdaQueryWrapper<articleUp> qqqq = new LambdaQueryWrapper<>();
        qqqq.eq(articleUp::getArticleId,id);
        articleUpMapper.delete(qqqq);
        //删除评论
        LambdaQueryWrapper<Discuss> discussLambdaQueryWrapper = new LambdaQueryWrapper<>();
        discussLambdaQueryWrapper.eq(Discuss::getArticleId,id);
        discussMapper.delete(discussLambdaQueryWrapper);
        //TODO 删除评论点赞


        int i = articleMapper.deleteById(id);
        if(i>=1){
            redisUtil.hdel(REDIS_ARTICLE, id.toString());
            return ApiResponse.success();
        }
        return ApiResponse.error(0,"删除失败");
    }


    @Override
    public ApiResponse<article> showbyid(Integer id) {
        RLock lock = redissonClient.getLock(String.valueOf(id));
        lock.lock();
        try{
            /**
             * 先更新数据库，再删除缓存，等下次，访问时，再次更新缓存
             *
             */
            // TODO 先删缓存，再更新数据库
            if(redisUtil.hHasKey(REDIS_ARTICLE, String.valueOf(id))){
                article article = (article) redisUtil.hget(REDIS_ARTICLE, String.valueOf(id));
//                System.out.println("fsdfsfffffffffffffffffffffffffffffffffffff");
                System.out.println(article);
                article.setViews(article.getViews()+1);
                articleMapper.updateById(article);
                System.out.println("redis");
                redisUtil.hset(REDIS_ARTICLE, String.valueOf(id), article);
                return ApiResponse.success(article);
            }else{
                article article = articleMapper.selectById(id);
                article.setViews(article.getViews()+1);
                articleMapper.updateById(article);
                System.out.println("mysql");
                redisUtil.hset(REDIS_ARTICLE, String.valueOf(id), article);
                return ApiResponse.success(article);
            }
        }finally {
            lock.unlock();
        }
    }

    @Override
    public ApiResponse<List<article>> showall() {
        QueryWrapper<article> q = new QueryWrapper<>();
//        q.eq("isshow",true);
        q.orderByDesc("up");
        List<article> articles = articleMapper.selectList(q);
        return ApiResponse.success(articles);
    }

    @Override
    public ApiResponse<List<article>> showone(String post) {
        QueryWrapper<article> q = new QueryWrapper<>();
//        q.eq("isshow",true);
        q.eq("post",post);
        List<article> articles = articleMapper.selectList(q);
        return ApiResponse.success(articles);
    }
}
