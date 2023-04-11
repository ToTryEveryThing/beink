package com.example.demo.controller.admin;

import com.example.demo.config.aop.checkRole.PermissionCheck;
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
    public Result upload(MultipartFile file,@RequestParam("keyPrefix") String keyPrefix) throws Exception {
        return ossService.uploadObject(file,keyPrefix);
    }

//    @GetMapping("/getToken/")
//    public OssTokenVo getToken() throws ClientException {
//        return ossService.getToken();
//    }
//
    @GetMapping("/getList/")
    public Result GetList(@RequestParam("keyPrefix") String keyPrefix){
        return ossService.redisList(keyPrefix);
    }
//+
    @PostMapping("/deleteImage/")
    @PermissionCheck
    public Result deleteImage (@RequestParam(value = "url",required = false) String url,
                               @RequestParam("keyPrefix") String keyPrefix) {
         return ossService.deleteObject(url,keyPrefix);
    }

}
