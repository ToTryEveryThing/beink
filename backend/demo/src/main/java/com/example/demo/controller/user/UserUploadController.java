package com.example.demo.controller.user;

import com.example.demo.config.aop.operationLog.MyLog;
import com.example.demo.config.aop.userInfo.UserInfo;
import com.example.demo.controller.common.ApiResponse;
import com.example.demo.service.admin.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
public class UserUploadController {

    @Autowired
    private OssService ossService;


    @MyLog
    @PostMapping("/user/upload/image/")
    public ApiResponse uopp(@UserInfo String name, @RequestBody MultipartFile file) throws IOException {
        return ossService.userupload(file,"userimage",name);
    }
}
