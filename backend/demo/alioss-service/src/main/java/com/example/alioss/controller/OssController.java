package com.example.alioss.controller;

;
import com.example.alioss.service.OssService;
import com.example.common.config.operationLog.MyLog;
import com.example.common.constants.response.ApiResponse;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/admin/oss")
public class OssController {

    @Autowired
    private OssService ossService;

//+
    @MyLog
    @PostMapping("/uploadImage/")
    @ApiOperation("上传图片")
    public ApiResponse<Set<Object>> upload(MultipartFile file, @RequestParam("keyPrefix") String keyPrefix) throws Exception {
        return ossService.uploadObject(file,keyPrefix);
    }

//    @GetMapping("/getToken/")
//    public OssTokenVo getToken() throws ClientException {
//        return ossService.getToken();
//    }
//
    @GetMapping("/getList/")
    @ApiOperation("图片列表")
    public ApiResponse<Set<Object>> GetList(@RequestParam("keyPrefix") String keyPrefix){
        return ossService.redisList(keyPrefix);
    }
//+
    @PostMapping("/deleteImage/")
    @MyLog
    @ApiOperation("删除图片")
    public ApiResponse<Set<Object>> deleteImage (@RequestParam(value = "url",required = false) String url,
                               @RequestParam("keyPrefix") String keyPrefix) {
         return ossService.deleteObject(url,keyPrefix);
    }

    @MyLog
    @PostMapping("/policy/")
    @ApiOperation("前后合作上传")
    public ApiResponse<Map<String, String>> policy() {
        return ossService.policyyyy();
    }

}
