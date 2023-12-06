package com.example.article.controller;


import com.example.article.pojo.ArticleES;
import com.example.article.pojo.Article;
import com.example.article.service.ArticleService;
import com.example.article.service.impl.ElasticSearchArticleImpl;
import com.example.common.config.limitApi.AccessLimit;
import com.example.common.config.operationLog.MyLog;
import com.example.common.constants.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/***
 * @author 睡醒继续做梦
 * @date 2023/3/30 17:37
 */

@RestController
@RequestMapping("/user/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    ElasticSearchArticleImpl elasticSearchArticle;

    @PostMapping("/add/")
    @MyLog
    public ApiResponse<Void> add(@RequestParam String content,
                                 @RequestParam String name,
                                 @RequestParam String title){
        return articleService.add(content, name, title);
    }

    @PostMapping("/edit/")
    @MyLog
    public ApiResponse<Void> edit(@RequestParam Integer id,
                                  @RequestParam String name,
                                  @RequestParam String post,
                                  @RequestParam String content,
                                  @RequestParam String title,
                                  @RequestParam Boolean show){
        if(!post.equals(name))return ApiResponse.error(0,"error");
        return articleService.edit(id, name, post, content, title, show);
    }

    @PostMapping("/delete/")
    @MyLog
    public ApiResponse<Void> delete(@RequestParam Integer id, @RequestParam String name,
                                    @RequestParam String post){
        if(!post.equals(name))return  ApiResponse.error(0,"error");
        return articleService.delete(id, name);
    }

    @AccessLimit(seconds = 60*60,maxCount = 1000000)
    @PostMapping("/showbyid/")
    public ApiResponse<Article> showbyid(@RequestParam Integer id){
        return articleService.showbyid(id);
    }


    @PostMapping("/showall/")
    public ApiResponse<List<Article>> showall(){
        return articleService.showall();
    }


    @PostMapping("/showone/")
    public ApiResponse<List<Article>> showone(@RequestParam String post){
        return articleService.showone(post);
    }


    @GetMapping("/{content}/")
    public ApiResponse<List<ArticleES>> searchHitsApiResponse(@PathVariable String content){
        return elasticSearchArticle.searchArticle(content);
    }





}
