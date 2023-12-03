package com.example.article.service.impl;


import com.example.article.mapper.ArticleMapper;
import com.example.article.pojo.ArticleES;
import com.example.article.pojo.Article;
import com.example.article.service.ElasticSearchArticleService;
import com.example.common.constants.response.ApiResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ElasticSearchArticleImpl implements ElasticSearchArticleService {

    @Autowired
    private ElasticsearchRestTemplate es;


    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public void together() {
        List<Article> Articles = articleMapper.selectList(null);
        List<ArticleES> list = new ArrayList<>();
        Articles.forEach(article -> {
            ArticleES articleES = new ArticleES();
            BeanUtils.copyProperties(article, articleES);
            list.add(articleES);
        });
        es.save(list);
        System.out.println("elasticsearch ---over");

    }

    public void createIndex(){
        boolean indexExists = es.indexOps(ArticleES.class).exists();
        if (!indexExists) {
            es.indexOps(ArticleES.class).create();
        }

    }

    @Override
    public ApiResponse<List<ArticleES>> searchArticle(String content) {
        PageRequest of = PageRequest.of(0, 50);//一页50个
        NativeSearchQueryBuilder query = new NativeSearchQueryBuilder();
        query.withPageable(of);
        query.withQuery(QueryBuilders.matchQuery("content",content));
        // 添加高亮信息到查询
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("content"); // 指定需要高亮的字段
        highlightBuilder.preTags("<em style=\"color:red;\">"); // 设置高亮前缀
        highlightBuilder.postTags("</em>"); // 设置高亮后缀
        query.withHighlightBuilder(highlightBuilder);
        SearchHits<ArticleES> search = es.search(query.build(), ArticleES.class);

        List<ArticleES> res = new ArrayList<>();

        for (SearchHit<ArticleES> searchHit : search) {
            ArticleES article = searchHit.getContent();
            // 从高亮结果中获取需要高亮的字段内容
            Map<String, List<String>> highlightFields = searchHit.getHighlightFields();
            if (highlightFields.containsKey("content")) {
                List<String> titleHighlights = highlightFields.get("content");
                // 获取高亮的标题内容
                // TODO 将多个关键字 分开  过滤特殊字符
                for (String highlightedContent : titleHighlights) {
                    // 创建新的ArticleES对象，并设置高亮内容
                    ArticleES article1 = new ArticleES();
                    BeanUtils.copyProperties(article,article1);
                    article1.setContent(highlightedContent);
                    res.add(article1);
                }
            }
        }
        return ApiResponse.success(res);
    }
}
