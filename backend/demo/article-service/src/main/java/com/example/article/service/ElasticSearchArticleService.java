package com.example.article.service;


import com.example.article.pojo.ArticleES;
import com.example.common.constants.response.ApiResponse;
import org.springframework.data.elasticsearch.core.SearchHits;

import java.util.List;

public interface ElasticSearchArticleService {

    public void together();

    public ApiResponse<List<ArticleES>> searchArticle(String content);

}
