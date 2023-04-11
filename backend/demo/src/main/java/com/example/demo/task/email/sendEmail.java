package com.example.demo.task.email;

import com.example.demo.service.impl.utils.MailService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;

/***
 * @author 睡醒继续做梦
 * @date 2023/4/4 17:52
 */
@Configuration
//@EnableScheduling
public class sendEmail {

    @Resource
    private MailService mailService;

    @Scheduled(fixedRate = 1000*1,initialDelay = 1000*1)
    public void sendEmail (){
        System.out.println("我是一");
//        mailService.sendSimpleMail("849299509@qq.com","4656555");
    }

    @Scheduled(fixedRate = 1000*3,initialDelay = 1000*3)
    public void rerer (){
        System.out.println("我是二");
//        mailService.sendSimpleMail("849299509@qq.com","4656555");
    }

}
