package com.example.article.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.example.article.mapper.ArticleMapper;
import com.example.article.mapper.ArticleUpMapper;
import com.example.article.mapper.DiscussMapper;
import com.example.article.pojo.Discuss;
import com.example.article.pojo.Article;
import com.example.article.pojo.ArticleUp;
import com.example.article.service.ArticleService;
import com.example.common.constants.response.ApiResponse;
import com.example.common.mapper.WebMapper;
import com.example.common.pojo.web;
import com.example.common.utils.redisUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.common.constants.radis.redisConstants.REDIS_ARTICLE;


/***
 * @author 睡醒继续做梦
 * @date 2023/3/30 18:02
 */
@Service
public class articleImpl implements ArticleService {

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    WebMapper webMapper;

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

    @Autowired
    private FansServiceImpl fansService;


    @Override
    public ApiResponse<Void> add(String content, String name, String title) {
        Article addArticle = new Article(0, name, title, content, 0,0 ,0, new Date(),true,0);
        int insert = articleMapper.insert(addArticle);
        // mybatis plus  可以从实体类中获取id 666
        redisUtil.hset(REDIS_ARTICLE, String.valueOf(addArticle.getId()),addArticle);
        //给自己的粉丝发送文章信息
        fansService.addRelationship(name,title ,nametoid(name), addArticle.getId());
        if(insert>=1)
            return ApiResponse.success();
        return  ApiResponse.error(0,"添加失败");
    }

    @Override
    public ApiResponse<Void> edit(Integer id, String name, String post, String content, String title, Boolean show) {
        Article article = new Article();
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
        LambdaQueryWrapper<ArticleUp> qqqq = new LambdaQueryWrapper<>();
        qqqq.eq(ArticleUp::getArticleId,id);
        articleUpMapper.delete(qqqq);
        //删除评论
        LambdaQueryWrapper<Discuss> discussLambdaQueryWrapper = new LambdaQueryWrapper<>();
        discussLambdaQueryWrapper.eq(Discuss::getArticleId,id);
        discussMapper.delete(discussLambdaQueryWrapper);
        //TODO 删除用户时删除评论点赞
        /*
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
    public ApiResponse<Article> showbyid(Integer id) {
        RLock lock = redissonClient.getLock(String.valueOf(id));
        lock.lock();
        try{
            if(redisUtil.hHasKey(REDIS_ARTICLE, String.valueOf(id))){
                Article article = (Article) redisUtil.hget(REDIS_ARTICLE, String.valueOf(id));
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
    public ApiResponse<List<Article>> showall() {
        List<Article> Articles = selectFromRedis();
        return ApiResponse.success(Articles);
    }

    @Override
    public ApiResponse<List<Article>> showone(String post) {

        QueryWrapper<web> ww = new QueryWrapper<>();
        ww.eq("account", post);
        web web = webMapper.selectOne(ww);
        if(web==null||web.equals("")){
            return ApiResponse.error(ApiResponse.Status.BAD_REQUEST);
        }

        QueryWrapper<Article> q = new QueryWrapper<>();
//        q.eq("isshow",true);
        q.eq("post",post);
        List<Article> Articles = articleMapper.selectList(q);
        return ApiResponse.success(Articles);
    }


    private List<Article> selectFromRedis(){

        HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();

        // 获取Hash结构中的所有值
        List<Object> values = hashOperations.values(REDIS_ARTICLE);

        //把空的过滤掉 因为他是缓存击穿 存的值
        List<Article> collect = values.stream()
                .filter(s->s!=null)
                .map(s -> new ObjectMapper().convertValue(s, Article.class))
                .sorted(Comparator.comparing(Article::getUp).reversed())//降序
                .collect(Collectors.toList());
        return collect;
    }

    Integer nametoid(String name){
        QueryWrapper<web> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account",name);
        web web = webMapper.selectOne(queryWrapper);
        return web.getId();
    }

}
