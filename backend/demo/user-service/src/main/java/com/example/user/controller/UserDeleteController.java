package com.example.user.controller;



import com.example.common.config.operationLog.MyLog;
import com.example.common.constants.response.ApiResponse;
import com.example.user.service.UserDeleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.Map;

@RestController
public class UserDeleteController {

    @Autowired
    private UserDeleteService userDeleteService;
//+
    @PostMapping("/user/admin/delete/")
    @MyLog
    public ApiResponse<Void> delete(@RequestParam Map<String , String> map) {
        int id = Integer.parseInt(map.get("id"));
        return userDeleteService.delete(id);
    }

    @PostMapping("/user/admin/multiple/")
    @MyLog
    public ApiResponse<Void> DeleteMultiple(@RequestParam String ids){
        System.out.println(ids);
        return userDeleteService.deleteMultiple(ids);
    }



}
