package com.example.demo.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.config.aop.checkRole.PermissionCheck;
import com.example.demo.config.aop.operationLog.MyLog;
import com.example.demo.controller.common.ApiResponse;
import com.example.demo.service.web.updataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author 睡醒继续做梦
 * @date 2022/12/5
 */
@RestController
public class UpdataController {
    @Autowired
    private updataService updataService;

    @PermissionCheck
    @PostMapping("/user/account/updata/")
    @MyLog
    public ApiResponse<String> updata(@RequestParam Map<String,String> map){
        int id = Integer.parseInt(map.get("id"));
        String account = map.get("account");
        String backimg = map.get("backimg");
        String role = map.get("role");
        Boolean enable = Boolean.valueOf(map.get("enable"));
        return updataService.Updata(id,account,backimg,role,enable);
    }

    @MyLog
    @PostMapping("/user/account/update/")
    public ApiResponse update(@RequestParam("all") String map){

        return updataService.update(map);
    }

}
