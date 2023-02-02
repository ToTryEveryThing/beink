package com.example.demo.controller.user;


import com.example.demo.controller.common.Result;
import com.example.demo.service.web.InfoService;
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
     * @return
     */
    @GetMapping("/user/account/info/")
    public Result getInfo(){
        return infoService.getinfo();
    }
}
