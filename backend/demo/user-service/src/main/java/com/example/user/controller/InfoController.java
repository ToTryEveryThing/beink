package com.example.user.controller;

import com.example.common.constants.response.ApiResponse;
import com.example.user.service.InfoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class InfoController {

    @Autowired
    private InfoService infoService;

    /**
     * 使用token 获取全部信息
     *
     * @return
     */
    @GetMapping("/user/account/info/")
    @ApiOperation("token换信息")
    public ApiResponse<Map<String, String>> getInfo(){
        return infoService.getinfo();
    }
}
