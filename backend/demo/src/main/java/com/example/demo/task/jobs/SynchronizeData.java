package com.example.demo.task.jobs;

import com.example.demo.mapper.article.ArticleMapper;
import com.example.demo.pojo.article.article;
import com.example.demo.service.impl.web.article.ElasticSearchArticleImpl;
import com.example.demo.utils.redisUtil;
import lombok.extern.log4j.Log4j2;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.example.demo.constants.radis.redisConstants.REDIS_ARTICLE;

/**
 * @author ToTryEveryThing
 * @date 2023/11/21 12:07
 * @Description
 */
@Component
@Log4j2
public class SynchronizeData implements Job {


    @Autowired
    ArticleMapper articleMapper;


    @Autowired
    com.example.demo.utils.redisUtil redisUtil;


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        redisUtil.del(REDIS_ARTICLE);
        List<article> articles = articleMapper.selectList(null);
        articles.forEach(s->
                redisUtil.hset(REDIS_ARTICLE, String.valueOf(s.getId()),s)
        );
        log.warn("redis 同步完成");
    }
}
