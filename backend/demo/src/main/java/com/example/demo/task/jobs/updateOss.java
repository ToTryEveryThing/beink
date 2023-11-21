package com.example.demo.task.jobs;

import com.example.demo.service.admin.OssService;
import com.example.demo.service.impl.web.article.ElasticSearchArticleImpl;
import lombok.extern.log4j.Log4j2;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author ToTryEveryThing
 * @date 2023/11/21 9:50
 * @Description
 */
@Component
@Log4j2
public class updateOss implements Job {

    @Autowired
    OssService service;

    @Autowired
    private ElasticSearchArticleImpl elasticSearchArticle;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        service.getList("study");
        service.getList("background");
        log.warn(new Date() + "对象存储同步成功");
        //      同步es
        elasticSearchArticle.createIndex();
        elasticSearchArticle.together();
//        System.out.println("paopaopao.....");
    }
}
