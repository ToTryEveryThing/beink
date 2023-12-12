package com.example.user.client;

import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * @author ToTryEveryThing
 * @date 2023/12/6 9:46
 * @Description
 */
@FeignClient("article-service")
public interface FansClient {

        @PostMapping("/user/fans/sendarticle")
        List<JSONObject> sendarticle(Integer id);

}
