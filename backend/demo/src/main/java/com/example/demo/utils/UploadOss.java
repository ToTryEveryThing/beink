package com.example.demo.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author 睡醒继续做梦
 * @date 2022/12/20
 */
public class UploadOss {
    public static final String ali = "https://beink.oss-cn-beijing.aliyuncs.com/";
    public  static String uploadImage(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String endpoint = "https://oss-cn-beijing.aliyuncs.com";
        String id = "LTAI5tPgiEKX2XhJgACQXKWb";
        String key = "yGMX3cFOyVtuMetmhudhbS6vllyPwO";
        OSS  os = new OSSClientBuilder().build(endpoint,id,key);
        os.putObject(
                    "beink",
                originalFilename,
                file.getInputStream()
        );
        os.shutdown();
        return "ok";

    }
}
