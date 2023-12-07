package com.example.user.controller;

import com.example.common.config.operationLog.MyLog;
import com.example.common.constants.response.ApiResponse;
import com.example.user.service.UserUpdateService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author 睡醒继续做梦
 * @date 2022/12/5
 */
@RestController
public class UserUpdateController {
    @Autowired
    private UserUpdateService UserUpdateService;


    @PostMapping("/user/account/updata/")
    @ApiOperation("更新用户信息")
    @MyLog
    public ApiResponse<String> updata(@RequestParam Map<String,String> map){
        int id = Integer.parseInt(map.get("id"));
        String account = map.get("account");
        String backimg = map.get("backimg");
        String role = map.get("role");
        Boolean enable = Boolean.valueOf(map.get("enable"));
        return UserUpdateService.Updata(id,account,backimg,role,enable);
    }

    @MyLog
    @PostMapping("/user/account/update/")
    @ApiOperation("更新用户信息")
    public ApiResponse update(@RequestParam("all") String map){

        return UserUpdateService.update(map);
    }

}
