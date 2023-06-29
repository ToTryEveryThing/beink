package com.example.demo.service.impl.web.article;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.controller.common.ApiResponse;
import com.example.demo.mapper.article.ArticleMapper;
import com.example.demo.mapper.article.ArticleUpMapper;
import com.example.demo.mapper.article.DiscussMapper;
import com.example.demo.pojo.article.Discuss;
import com.example.demo.pojo.article.article;
import com.example.demo.pojo.article.articleUp;
import com.example.demo.service.web.article.articleService;
import com.example.demo.utils.redisUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public ApiResponse<Void> add(String content, String name, String title) {
        article addArticle = new article(0, name, title, content, 0,0 ,0, new Date(),true,0);
        int insert = articleMapper.insert(addArticle);
        // mybatis plus  可以从实体类中获取id 666
        redisUtil.hset(REDIS_ARTICLE, String.valueOf(addArticle.getId()),addArticle);
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
        /**
         * 可以在数据库中 使用外键约束
         * 也可以在后端 直接写
         * 待实现。。。
         */
        int i = articleMapper.deleteById(id);
        if(i>=1){
            redisUtil.hdel(REDIS_ARTICLE, id.toString());
            return ApiResponse.success();
        }
        return ApiResponse.error(0,"删除失败");
    }

    /**
     *  不出意外 id 一定会存在 如果不存在 缓存击穿
     * @param id
     * @return
     */
    @Override
    public ApiResponse<article> showbyid(Integer id) {
        RLock lock = redissonClient.getLock(String.valueOf(id));
        lock.lock();
        try{
            if(redisUtil.hHasKey(REDIS_ARTICLE, String.valueOf(id))){
                article article = (article) redisUtil.hget(REDIS_ARTICLE, String.valueOf(id));
                System.out.println(article);
                article.setViews(article.getViews()+1);
                articleMapper.updateById(article);
                System.out.println("redis");
                redisUtil.hset(REDIS_ARTICLE, String.valueOf(id), article);
                return ApiResponse.success(article);
            }else{
                /**
                 *  缓存击穿的话  给它个空值
                 */
                redisUtil.hset(REDIS_ARTICLE,String.valueOf(id),null);
                return ApiResponse.error(ApiResponse.Status.BAD_REQUEST);
            }
        }finally {
            lock.unlock();
        }
    }

    @Override
    public ApiResponse<List<article>> showall() {
        List<article> articles = selectFromRedis();
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


    private List<article> selectFromRedis(){

        HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();


        // 获取Hash结构中的所有值
        List<Object> values = hashOperations.values(REDIS_ARTICLE);

        //把空的过滤掉 因为他是缓存击穿 存的值
        List<article> collect = values.stream()
                .filter(s->s!=null)
                .map(s -> new ObjectMapper().convertValue(s, article.class))
                .collect(Collectors.toList());

        return collect;

    }
}
