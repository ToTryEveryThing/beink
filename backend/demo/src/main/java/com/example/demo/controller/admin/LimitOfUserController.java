package com.example.demo.controller.admin;

import com.example.demo.controller.common.ApiResponse;
import com.example.demo.pojo.user.LimitOfUser;
import com.example.demo.service.impl.admin.LimitOfUserService;
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
    public ApiResponse<Void> limit(LimitOfUser limitOfUser){

        return limitOfUserService.update(limitOfUser);

    }

    @PostMapping("/admin/limits/show/")
    public ApiResponse<Map<String, Boolean>> show(){
        return limitOfUserService.show();
    }

}
