package com.example.demo.controller.user;

import com.example.demo.aop.PermissionCheck;
import com.example.demo.controller.common.Result;
import com.example.demo.service.web.updataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author 睡醒继续做梦
 * @date 2022/12/5
 */
@RestController
public class UpdataController {
    @Autowired
    private updataService updataService;

    @PermissionCheck
    @PostMapping("/user/account/updata/")
    public Result updata(@RequestParam Map<String,String> map){
        int id = Integer.parseInt(map.get("id"));
        String account = map.get("account");
        String backimg = map.get("backimg");
        return updataService.Updata(id,account,backimg);
    }

}
