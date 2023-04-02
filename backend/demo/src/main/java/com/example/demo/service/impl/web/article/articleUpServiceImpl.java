package com.example.demo.service.impl.web.article;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.demo.controller.common.Result;
import com.example.demo.mapper.article.ArticleMapper;
import com.example.demo.mapper.article.ArticleUpMapper;
import com.example.demo.pojo.article.article;
import com.example.demo.pojo.article.articleUp;
import com.example.demo.service.web.article.articleUpService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/***
 * @author 睡醒继续做梦
 * @date 2023/4/2 10:41
 */

@Service
public class articleUpServiceImpl implements articleUpService {

    @Resource
    ArticleUpMapper articleUpMapper;

    @Resource
    ArticleMapper articleMapper;


    @Override
    public Result changeUp(String userName, Integer articleId, Integer status) {
        UpdateWrapper<article> q = new UpdateWrapper<>();
        LambdaUpdateWrapper<articleUp> qq = new LambdaUpdateWrapper<>();
        QueryWrapper<articleUp> aaaa = new QueryWrapper<>();
        if(status.equals(1)){
            q.setSql("up = up + 1").eq("id",articleId);
        }else{
            q.setSql("up = up - 1").eq("id",articleId);
        }
        articleMapper.update(null, q);

        aaaa.eq("article_id",articleId);
        if(articleUpMapper.selectOne(aaaa)!=null){
            qq.eq(articleUp::getArticleId, articleId);
            qq.set(articleUp::getStatus,status);
            articleUpMapper.update(null,qq);
        } else{
            articleUp articleUp = new articleUp();
            articleUp.setArticleId(articleId);
            articleUp.setStatus(status);
            articleUp.setUserName(userName);
            articleUpMapper.insert(articleUp);
        }
        return new Result(1,"success");

    }

    @Override
    public Result getStatus(Integer articleId, String userName) {
        QueryWrapper<articleUp> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.eq("article_id",articleId)
                            .eq("user_name",userName);
        if(articleUpMapper.selectOne(objectQueryWrapper)!=null){
            return new Result(1,"success",articleUpMapper.selectOne(objectQueryWrapper).getStatus());
        }
        return new Result(1,"success",0);
    }
}
