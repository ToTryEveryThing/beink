package com.example.demo.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.aop.limitApi.AccessLimit;
import com.example.demo.aop.userInfo.UserInfo;
import com.example.demo.controller.common.Result;
import com.example.demo.service.web.DiscussService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author 睡醒继续做梦
 * @date 2023/2/16
 */
@RestController
public class DiscussController {


    @Autowired
    private DiscussService discussService;

    @AccessLimit(seconds = 60*60,maxCount = 50)
    @PostMapping("/user/discuss/add/")
    public Result add(@UserInfo String userName, @RequestParam Map<String ,String> map){
        String content = map.get("content");
        Integer articleId = Integer.valueOf(map.get("article_id"));
        return discussService.addReply(content,articleId,userName);
    }

    @AccessLimit(seconds = 60*60,maxCount = 50)
    @PostMapping("/user/discuss/show/")
    public JSONObject show(@RequestParam Map<String ,String> map){
        return discussService.showReply(Integer.valueOf(map.get("article_id")),
                Integer.valueOf(map.get("page")));
    }

    @PostMapping("/user/discuss/delete/")
    public Result del(@UserInfo String userName,Integer id){
        return discussService.delReply(userName,id);
    }

}
