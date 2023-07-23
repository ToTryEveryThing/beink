package com.example.demo.task.ScheduledTasks;

import com.example.demo.mapper.article.ArticleMapper;
import com.example.demo.pojo.article.article;
import com.example.demo.service.admin.OssService;
import com.example.demo.service.impl.utils.MailService;
import com.example.demo.service.impl.web.article.ElasticSearchArticleImpl;
import com.example.demo.utils.redisUtil;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.List;

import static com.example.demo.constants.radis.redisConstants.REDIS_ARTICLE;

/***
 * @author 睡醒继续做梦
 * @date 2023/4/11 12:21
 */

@Configuration
@Log4j2
@EnableScheduling
public class tasks {

    /**
     * http://cron.ciding.cc/
     * 在线表达式
     */

    @Autowired
    OssService service;

    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    private ElasticSearchArticleImpl elasticSearchArticle;

    @Autowired
    redisUtil redisUtil;


    /**
     * 二十四小时 一次
     * 同步 oss
     */
    @Scheduled(fixedRate = 1000*60*60*24,initialDelay = 1000*3)
    public void updateOss(){
        service.getList("study");
        service.getList("background");
        log.warn(new Date() + "对象存储同步成功");
        //      同步es
        elasticSearchArticle.createIndex();
        elasticSearchArticle.together();
    }

    /**
     * 每天中午12点同步数据
     */
    @Scheduled(cron = "0 0 12 * * *")
    public void SynchronizeData(){
        redisUtil.del(REDIS_ARTICLE);
        List<article> articles = articleMapper.selectList(null);
        articles.forEach(s->
                redisUtil.hset(REDIS_ARTICLE, String.valueOf(s.getId()),s)
        );
        log.warn("redis 同步完成");


    }


}
