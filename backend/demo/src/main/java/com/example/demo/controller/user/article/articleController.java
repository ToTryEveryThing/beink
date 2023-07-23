package com.example.demo.controller.user.article;

import com.example.demo.config.aop.limitApi.AccessLimit;
import com.example.demo.config.aop.operationLog.MyLog;
import com.example.demo.config.aop.userInfo.UserInfo;
import com.example.demo.controller.common.ApiResponse;
import com.example.demo.controller.common.Result;
import com.example.demo.pojo.article.ArticleES;
import com.example.demo.pojo.article.article;
import com.example.demo.service.impl.web.article.ElasticSearchArticleImpl;
import com.example.demo.service.impl.web.article.articleImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/***
 * @author 睡醒继续做梦
 * @date 2023/3/30 17:37
 */

@RestController
public class articleController {

    @Autowired
    private articleImpl articleImpl;

    @Autowired
    ElasticSearchArticleImpl elasticSearchArticle;

    @PostMapping("/user/article/add/")
    @MyLog
    public ApiResponse<Void> add(@RequestParam String content,
                                 @UserInfo String name,
                                 @RequestParam String title){
        return articleImpl.add(content, name, title);
    }

    @PostMapping("/user/article/edit/")
    @MyLog
    public ApiResponse<Void> edit(@RequestParam Integer id,
                                  @UserInfo String name,
                                  @RequestParam String post,
                                  @RequestParam String content,
                                  @RequestParam String title,
                                  @RequestParam Boolean show){
        if(!post.equals(name))return ApiResponse.error(0,"error");
        return articleImpl.edit(id, name, post, content, title, show);
    }

    @PostMapping("/user/article/delete/")
    @MyLog
    public ApiResponse<Void> delete(@RequestParam Integer id, @UserInfo String name,
                                    @RequestParam String post){
        if(!post.equals(name))return  ApiResponse.error(0,"error");
        return articleImpl.delete(id, name);
    }

    @AccessLimit(seconds = 60*60,maxCount = 1000000)
    @PostMapping("/user/article/showbyid/")
    public ApiResponse<article> showbyid(@RequestParam Integer id){
        return articleImpl.showbyid(id);
    }


    @PostMapping("/user/article/showall/")
    public ApiResponse<List<article>> showall(){
        return articleImpl.showall();
    }


    @PostMapping("/user/article/showone/")
    public ApiResponse<List<article>> showone(@RequestParam String post){
        return articleImpl.showone(post);
    }


    @GetMapping("/search/article/{content}/")
    public ApiResponse<List<ArticleES>> searchHitsApiResponse(@PathVariable String content){
        return elasticSearchArticle.searchArticle(content);
    }





}
