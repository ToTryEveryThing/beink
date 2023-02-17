package com.example.demo.controller.user;

import com.alibaba.fastjson.JSONObject;
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

    @PostMapping("/user/discuss/add/")
    public Result add(@UserInfo String userName, @RequestParam Map<String ,String> map){
        String content = map.get("content");
        String postName = map.get("post_name");
        String postIndex = map.get("post_index");
        return discussService.addReply(content,postName,postIndex,userName);
    }

    @PostMapping("/user/discuss/show/")
    public JSONObject show(@RequestParam Map<String ,String> map){
        return discussService.showReply(map.get("post_name"),
                map.get("post_index"), Integer.valueOf(map.get("page")));
    }

    @PostMapping("/user/discuss/delete/")
    public Result del(@UserInfo String userName,Integer id){
        return discussService.delReply(userName,id);
    }

}
