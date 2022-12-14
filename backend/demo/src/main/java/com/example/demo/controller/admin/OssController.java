package com.example.demo.controller.admin;

import com.aliyuncs.exceptions.ClientException;
import com.example.demo.service.admin.OssService;
import com.example.demo.utils.IdandName;
import com.example.demo.vo.OssTokenVo;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author 睡醒继续做梦
 * @date 2022/12/20
 */
@RestController
@RequestMapping("/oss")
public class OssController {

    @Autowired
    private OssService ossService;

    @Autowired
    private HttpServletRequest request;
//+
    @PostMapping("uploadImage/")
    public List upload(MultipartFile file,@RequestParam String account) throws Exception {
        if(!new IdandName().admin(request.getHeader("Authorization"),account)){
            return null;
        }
        return ossService.uploadObject(file);

    }

//    @GetMapping("/getToken/")
//    public OssTokenVo getToken() throws ClientException {
//        return ossService.getToken();
//    }
//
    @GetMapping("/getList/")
    public List GetList(){
        return ossService.getList();
    }
//+
    @DeleteMapping("/deleteImage/")
    public List deleteImage (@RequestParam  Map<String,String> map) throws Exception {
        if(!new IdandName().admin(request.getHeader("Authorization"),map.get("account"))){
            return null;
        }
         return ossService.deleteObject(map.get("url"));
    }

}
