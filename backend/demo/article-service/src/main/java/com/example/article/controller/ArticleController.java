package com.example.article.controller;


import com.example.article.pojo.ArticleES;
import com.example.article.pojo.Article;
import com.example.article.service.ArticleService;
import com.example.article.service.impl.ElasticSearchArticleImpl;
import com.example.common.config.limitApi.AccessLimit;
import com.example.common.config.operationLog.MyLog;
import com.example.common.constants.response.ApiResponse;
import com.example.common.utils.UserContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation("添加文章")
    public ApiResponse<Void> add(@RequestParam String content,
                                 @RequestParam String title){
        String user = UserContext.getUser();
        System.out.println("哈哈哈哈哈哈哈  拿到啦");
        System.out.println("user = " + user);
        return articleService.add(content, user, title);
    }

    @PostMapping("/edit/")
    @MyLog
    @ApiOperation("修改文章")
    public ApiResponse<Void> edit(@RequestParam Integer id,
                                  @RequestParam String post,
                                  @RequestParam String content,
                                  @RequestParam String title,
                                  @RequestParam Boolean show){
        String user = UserContext.getUser();
        if(!post.equals(user))return ApiResponse.error(0,"error");
        return articleService.edit(id, user, post, content, title, show);
    }

    @PostMapping("/delete/")
    @MyLog
    @ApiOperation("删除文章")
    public ApiResponse<Void> delete(@RequestParam Integer id,
                                    @RequestParam String post){
        String user = UserContext.getUser();
        if(!post.equals(user))return  ApiResponse.error(0,"error");
        return articleService.delete(id, user);
    }

    @AccessLimit(seconds = 60*60,maxCount = 1000000)
    @PostMapping("/showbyid/")
    @ApiOperation("根据ID展示文章")
    public ApiResponse<Article> showbyid(@RequestParam Integer id){
        return articleService.showbyid(id);
    }


    @PostMapping("/showall/")
    @ApiOperation("展示所有文章")
    public ApiResponse<List<Article>> showall(){
        return articleService.showall();
    }


    @PostMapping("/showone/")
    public ApiResponse<List<Article>> showone(@RequestParam String post){
        return articleService.showone(post);
    }


    @GetMapping("/{content}/")
    @ApiOperation("将文章同步ES")
    public ApiResponse<List<ArticleES>> searchHitsApiResponse(@PathVariable String content){
        return elasticSearchArticle.searchArticle(content);
    }





}
