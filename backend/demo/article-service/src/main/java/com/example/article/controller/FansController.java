package com.example.article.controller;



import com.alibaba.fastjson.JSONObject;
import com.example.article.service.FansService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api("Openfeign")
@RestController
@RequestMapping("/user/fans")
public class FansController {

    @Autowired
    private FansService fansService;

    @ApiOperation("test")
    @PostMapping("/sendarticle")
    public List<JSONObject> sendArticle(Integer id){
        return fansService.sendArticle(id);
    }





}
