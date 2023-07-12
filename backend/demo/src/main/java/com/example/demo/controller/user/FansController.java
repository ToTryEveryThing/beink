package com.example.demo.controller.user;


import com.example.demo.controller.common.ApiResponse;
import com.example.demo.service.impl.web.user.FansServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/fans")
public class FansController {

    @Autowired
    private FansServiceImpl fansService;






}
