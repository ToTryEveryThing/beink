package com.example.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 睡醒继续做梦
 * @date 2022/12/20
 */
@Component
@Data
@ConfigurationProperties(prefix = "alioss")
public class AliossConfig {
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecert;
    private String roleArn;
    private String  regionID;
    private String bucket;
}
