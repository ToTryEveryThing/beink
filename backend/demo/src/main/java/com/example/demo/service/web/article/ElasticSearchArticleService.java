package com.example.demo.service.web.article;

import com.example.demo.controller.common.ApiResponse;
import com.example.demo.pojo.article.ArticleES;
import org.springframework.data.elasticsearch.core.SearchHits;

import java.util.List;

public interface ElasticSearchArticleService {

    public void together();

    public ApiResponse<List<ArticleES>> searchArticle(String content);

}
