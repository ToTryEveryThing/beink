package com.example.demo.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.config.aop.operationLog.MyLog;
import com.example.demo.controller.common.ApiResponse;
import com.example.demo.pojo.Log;
import com.example.demo.pojo.user.LimitOfUser;
import com.example.demo.service.impl.admin.getOperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/***
 * @author 睡醒继续做梦
 * @date 2023/4/30 11:49
 */

@RestController
public class OperationLogController {

    @Autowired
    private getOperationLogService getOperationLogService;

    @GetMapping("/admin/log/")
    public JSONObject getLog(@RequestParam  Integer page){

        return getOperationLogService.getLog(page);
    }

}
