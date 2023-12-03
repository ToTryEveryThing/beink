package com.example.article.controller;



import com.example.article.service.impl.FansServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/fans")
public class FansController {

    @Autowired
    private FansServiceImpl fansService;






}
