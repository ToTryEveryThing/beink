package com.example.user.controller;

import com.example.common.constants.response.ApiResponse;
import com.example.user.domain.LimitOfUser;
import com.example.user.service.impl.LimitOfUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/***
 * @author 睡醒继续做梦
 * @date 2023/5/11 14:03
 */
@RestController
public class LimitOfUserController {

    @Autowired
    private LimitOfUserService limitOfUserService;

    @PostMapping("/admin/limits/update/")
    @ApiOperation("是否注册与聊天")
    public ApiResponse<Void> limit(LimitOfUser limitOfUser){

        return limitOfUserService.update(limitOfUser);

    }

    @PostMapping("/admin/limits/show/")
    @ApiOperation("展示是否注册与聊天")
    public ApiResponse<Map<String, Boolean>> show(){
        return limitOfUserService.show();
    }

}
