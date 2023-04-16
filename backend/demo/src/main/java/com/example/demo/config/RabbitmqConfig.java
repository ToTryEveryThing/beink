package com.example.demo.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/***
 * @author 睡醒继续做梦
 * @date 2023/4/15 9:04
 */
@Configuration
public class RabbitmqConfig {

    public static final String EXCHANGE_NAME = "direct";
    public static final String ARTICLE_QUEUE = "article-direct-queue";
    public static final String ARTICLE_DIRECT_ROUTE = "article";

    /**
     * 将交换机与队列绑定
     */
//    @Bean
//    public Binding emailBinding() {
//        return BindingBuilder
//                .bind(new Queue(ARTICLE_QUEUE, true))
//                .to(new DirectExchange(EXCHANGE_NAME, true, false))
//                .with(ARTICLE_DIRECT_ROUTE);
//    }

//}
    @Bean
    public DirectExchange directExchange() {
        /**
         * directExchange的参数说明:
         * 1. 交换机名称
         * 2. 是否持久化 true：持久化，交换机一直保留 false：不持久化，用完就删除
         * 3. 是否自动删除 false：不自动删除 true：自动删除
         */
        return new DirectExchange(EXCHANGE_NAME, true, false);
    }


    @Bean
    public Queue emailQueue() {
        /*
         Queue构造函数参数说明
         1. 队列名
         2. 是否持久化 true：持久化 false：不持久化
        */
        return new Queue(ARTICLE_QUEUE, true);
    }

    @Bean
    public Binding emailBinding() {
        return BindingBuilder.bind(
                        emailQueue())
                .to(directExchange())
                .with(ARTICLE_DIRECT_ROUTE
                );
    }

}
