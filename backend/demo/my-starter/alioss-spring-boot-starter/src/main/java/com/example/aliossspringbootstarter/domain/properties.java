package com.example.aliossspringbootstarter.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author ToTryEveryThing
 * @date 2023/12/14 9:09
 * @Description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "ali.oss")
public class properties {
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecert;
    private String roleArn;
    private String  regionID;
    private String bucket;
}
