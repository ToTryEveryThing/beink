package com.example.aliossspringbootstarter.config;


import com.example.aliossspringbootstarter.util.AliossUtil;
import com.example.aliossspringbootstarter.domain.properties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ToTryEveryThing
 * @date 2023/12/14 9:16
 * @Description
 */
@Configuration
@EnableConfigurationProperties(properties.class)
public class AliConfig {

    @Bean
    public AliossUtil aliossUtil(properties properties){
        return new AliossUtil(properties);
    }


}
