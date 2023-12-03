package com.example.article.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.article.mapper.ArticleMapper;
import com.example.article.mapper.ArticleUpMapper;
import com.example.article.pojo.Article;
import com.example.article.pojo.ArticleUp;
import com.example.article.service.ArticleUpService;
import com.example.common.constants.response.ApiResponse;
import com.example.common.utils.redisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static com.example.common.constants.radis.redisConstants.REDIS_ARTICLE;


/***
 * @author 睡醒继续做梦
 * @date 2023/4/2 10:41
 */

@Service
public class articleUpServiceImpl implements ArticleUpService {

    @Resource
    ArticleUpMapper articleUpMapper;

    @Resource
    ArticleMapper articleMapper;

    @Autowired
    redisUtil redisUtil;


    @Override
    @Transactional
    public ApiResponse<Void> changeUp(String userName, Integer articleId, Integer status) {

        UpdateWrapper<Article> q = new UpdateWrapper<>();
        LambdaUpdateWrapper<ArticleUp> qq = new LambdaUpdateWrapper<>();
        QueryWrapper<ArticleUp> aaaa = new QueryWrapper<>();
        if(status.equals(1)){
            q.setSql("up = up + 1").eq("id",articleId);
        }else{
            q.setSql("up = up - 1").eq("id",articleId);
        }
        // 点赞 存入数据库
        int update = articleMapper.update(null, q);

        //改变自己给这个文章的点赞状态
        aaaa.eq("article_id",articleId);
        aaaa.eq("user_name", userName);
        if(articleUpMapper.selectOne(aaaa)!=null){ //点过赞
            qq.eq(ArticleUp::getArticleId, articleId);
            qq.set(ArticleUp::getStatus,status);
            articleUpMapper.update(null,qq);
        } else{  //没有点过赞
            ArticleUp articleUp = new ArticleUp();
            articleUp.setArticleId(articleId);
            articleUp.setStatus(status);
            articleUp.setUserName(userName);
            articleUpMapper.insert(articleUp);
        }

        // 如果点赞操作成功，更新redis
        if(update>=1){
            Article article = (Article) redisUtil.hget(REDIS_ARTICLE, String.valueOf(articleId));
            if(status.equals(1)) article.setUp(article.getUp()+1);
            else article.setUp(article.getUp()-1);
            redisUtil.hset(REDIS_ARTICLE, String.valueOf(articleId), article);
        }

        return ApiResponse.success();

    }

    @Override
    public ApiResponse<Integer> getStatus(Integer articleId, String userName) {
        QueryWrapper<ArticleUp> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.eq("article_id",articleId)
                            .eq("user_name",userName);
        if(articleUpMapper.selectOne(objectQueryWrapper)!=null){
            return ApiResponse.success(articleUpMapper.selectOne(objectQueryWrapper).getStatus());
        }
        return ApiResponse.success(0);
    }
}
