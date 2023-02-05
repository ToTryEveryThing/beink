package com.example.demo.pojo;

import lombok.Builder;
import lombok.Data;

/**
 * @author 睡醒继续做梦
 * @date 2022/12/20
 */
@Data
@Builder
public class OssTokenVo {
    private String accessKeyId;
    private String accessKeySecert;
    private String stsToken;
    private String region;
    private String bucket;
}
