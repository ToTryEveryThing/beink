package com.example.demo.controller.admin;

import com.example.demo.aop.checkRole.PermissionCheck;
import com.example.demo.controller.common.Result;
import com.example.demo.service.admin.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 睡醒继续做梦
 * @date 2022/12/20
 */
@RestController
@RequestMapping("/oss")
public class OssController {

    @Autowired
    private OssService ossService;

//+
    @PermissionCheck
    @PostMapping("uploadImage/")
    public Result upload(MultipartFile file) throws Exception {
        return ossService.uploadObject(file);
    }

//    @GetMapping("/getToken/")
//    public OssTokenVo getToken() throws ClientException {
//        return ossService.getToken();
//    }
//
    @GetMapping("/getList/")
    public Result GetList(){
        return ossService.redisList();
    }
//+
    @PostMapping("/deleteImage/")
    @PermissionCheck
    public Result deleteImage (@RequestParam(value = "url",required = false) String url) {
         return ossService.deleteObject(url);
    }

}
