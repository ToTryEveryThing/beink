package com.example.demo.service.impl.web.article;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.demo.controller.common.ApiResponse;
import com.example.demo.mapper.article.ArticleMapper;
import com.example.demo.mapper.article.ArticleUpMapper;
import com.example.demo.pojo.article.article;
import com.example.demo.pojo.article.articleUp;
import com.example.demo.service.web.article.articleUpService;
import com.example.demo.utils.redisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static com.example.demo.constants.radis.redisConstants.REDIS_ARTICLE;

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

    @Autowired
    redisUtil redisUtil;


    @Override
    @Transactional
    public ApiResponse<Void> changeUp(String userName, Integer articleId, Integer status) {

        UpdateWrapper<article> q = new UpdateWrapper<>();
        LambdaUpdateWrapper<articleUp> qq = new LambdaUpdateWrapper<>();
        QueryWrapper<articleUp> aaaa = new QueryWrapper<>();
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
            qq.eq(articleUp::getArticleId, articleId);
            qq.set(articleUp::getStatus,status);
            articleUpMapper.update(null,qq);
        } else{  //没有点过赞
            articleUp articleUp = new articleUp();
            articleUp.setArticleId(articleId);
            articleUp.setStatus(status);
            articleUp.setUserName(userName);
            articleUpMapper.insert(articleUp);
        }

        // 如果点赞操作成功，更新redis
        if(update>=1){
            article article = (article) redisUtil.hget(REDIS_ARTICLE, String.valueOf(articleId));
            if(status.equals(1)) article.setUp(article.getUp()+1);
            else article.setUp(article.getUp()-1);
            redisUtil.hset(REDIS_ARTICLE, String.valueOf(articleId), article);
        }

        return ApiResponse.success();

    }

    @Override
    public ApiResponse<Integer> getStatus(Integer articleId, String userName) {
        QueryWrapper<articleUp> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.eq("article_id",articleId)
                            .eq("user_name",userName);
        if(articleUpMapper.selectOne(objectQueryWrapper)!=null){
            return ApiResponse.success(articleUpMapper.selectOne(objectQueryWrapper).getStatus());
        }
        return ApiResponse.success(0);
    }
}
