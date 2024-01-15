package com.example.demo.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/***
 * @author 睡醒继续做梦
 * @date 2023/4/6 13:57
 */
@Configuration
public class RedissonConfig {

    @Value("${redisson.key}")
    private String key;

    @Value("${spring.redis.host}")
    private String host;
    @Bean
    RedissonClient redisson() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://"+host+":6379").setDatabase(0).setPassword(key);
        return Redisson.create(config);
    }
}
