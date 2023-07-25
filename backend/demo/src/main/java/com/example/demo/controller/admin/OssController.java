package com.example.demo.controller.admin;

import com.example.demo.config.aop.operationLog.MyLog;
import com.example.demo.controller.common.ApiResponse;
import com.example.demo.controller.common.Result;
import com.example.demo.service.admin.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Set;

/**
 * @author 睡醒继续做梦
 * @date 2022/12/20
 */
@RestController
public class OssController {

    @Autowired
    private OssService ossService;

//+
    @MyLog
    @PostMapping("/admin/oss/uploadImage/")
    public ApiResponse<Set<Object>> upload(MultipartFile file, @RequestParam("keyPrefix") String keyPrefix) throws Exception {
        return ossService.uploadObject(file,keyPrefix);
    }

//    @GetMapping("/getToken/")
//    public OssTokenVo getToken() throws ClientException {
//        return ossService.getToken();
//    }
//
    @GetMapping("/oss/getList/")
    public ApiResponse<Set<Object>> GetList(@RequestParam("keyPrefix") String keyPrefix){
        return ossService.redisList(keyPrefix);
    }
//+
    @PostMapping("/admin/oss/deleteImage/")
    @MyLog
    public ApiResponse<Set<Object>> deleteImage (@RequestParam(value = "url",required = false) String url,
                               @RequestParam("keyPrefix") String keyPrefix) {
         return ossService.deleteObject(url,keyPrefix);
    }

    @MyLog
    @PostMapping("/admin/oss/policy/")
    public ApiResponse<Map<String, String>> policy() {
        return ossService.policyyyy();
    }

}
