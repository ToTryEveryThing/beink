package com.example.demo.controller.user.article;

import com.example.demo.config.aop.checkRole.PermissionCheck;
import com.example.demo.config.aop.limitApi.AccessLimit;
import com.example.demo.config.aop.userInfo.UserInfo;
import com.example.demo.controller.common.Result;
import com.example.demo.service.impl.web.article.articleImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/***
 * @author 睡醒继续做梦
 * @date 2023/3/30 17:37
 */

@RestController
public class articleController {

    @Autowired
    private articleImpl article;

    @PostMapping("/user/article/add/")
    public Result add(@RequestParam String content,
                      @UserInfo String name,
                      @RequestParam String title){
        return article.add(content, name, title);
    }

    @PostMapping("/user/article/edit/")
    public Result edit(@RequestParam Integer id,
                       @UserInfo String name,
                       @RequestParam String post,
                       @RequestParam String content,
                       @RequestParam String title){
        if(!post.equals(name))return new Result(0,"error");
        return article.edit(id, name, post, content, title);
    }

    @PostMapping("/user/article/delete/")
    public Result delete(@RequestParam Integer id, @UserInfo String name,
                         @RequestParam String post){
        if(!post.equals(name))return new Result(0,"error");
        return article.delete(id, name);
    }

    @AccessLimit(seconds = 60*60,maxCount = 1000000)
    @PostMapping("/user/article/showbyid/")
    public Result showbyid(@RequestParam Integer id){
        return article.showbyid(id);
    }


    @PostMapping("/user/article/showall/")
    public Result showall(){
        return article.showall();
    }


    @PostMapping("/user/article/showone/")
    public Result showone(@RequestParam String post){
        return article.showone(post);
    }




}
