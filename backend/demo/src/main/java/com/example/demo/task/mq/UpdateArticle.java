package com.example.demo.task.mq;

import com.example.demo.utils.redisUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.example.demo.config.RabbitmqConfig.ARTICLE_QUEUE;

/***
 * @author 睡醒继续做梦
 * @date 2023/4/15 9:05
 */

@Component
public class UpdateArticle {

    @Autowired
    redisUtil redisUtil;

    /**
     * 获得article的数据，更新数据库和redis
     * @param msg
     */
//    @RabbitListener(queues = ARTICLE_QUEUE)
    public void hh(String msg){
        System.out.println(msg);
    }

}
