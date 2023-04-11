package com.example.demo.task.oss;

import com.example.demo.service.admin.OssService;
import com.example.demo.service.impl.utils.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/***
 * @author 睡醒继续做梦
 * @date 2023/4/11 12:21
 */

@Configuration
@EnableScheduling
public class getOSS {

    @Autowired
    OssService service;

    @Autowired
    private MailService mailService;


    /**
     * 二十四小时 一次
     */
    @Scheduled(fixedRate = 1000*60*60*24,initialDelay = 1000*3)
    public void updateOss(){
        service.getList("study");
        service.getList("background");
        mailService.sendRedisOss("849299509@qq.com");
        System.out.println("应该ok了");
    }
}
