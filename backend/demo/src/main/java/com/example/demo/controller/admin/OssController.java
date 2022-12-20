package com.example.demo.controller.admin;

import com.aliyuncs.exceptions.ClientException;
import com.example.demo.service.admin.OssService;
import com.example.demo.vo.OssTokenVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 睡醒继续做梦
 * @date 2022/12/20
 */
@RestController
@RequestMapping("/oss")
public class OssController {

    @Autowired
    private OssService ossService;


    @GetMapping("/getToken/")
    public OssTokenVo getToken() throws ClientException {
        return ossService.getToken();
    }

}
