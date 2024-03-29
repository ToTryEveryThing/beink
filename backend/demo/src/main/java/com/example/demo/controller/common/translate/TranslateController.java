package com.example.demo.controller.common.translate;

import com.example.demo.config.aop.limitApi.AccessLimit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class TranslateController {

    @Autowired
    private TransApi transApi;

    @AccessLimit(seconds = 7200, maxCount = 20)
    @PostMapping("/translate/query/")
    public String query(@RequestParam("query") String query,
                        @RequestParam("from") String from,
                        @RequestParam("to") String to) {
        System.out.println(query);
        return transApi.getTransResult(query,from,to);
    }

}

