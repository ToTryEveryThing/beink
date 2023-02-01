package com.example.demo.controller.admin;


import com.example.demo.aop.PermissionCheck;
import com.example.demo.service.admin.userDeleteService;
import com.example.demo.utils.IdandName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class userDeleteController {

    @Autowired
    private userDeleteService userDeleteService;
//+
    @PostMapping("/user/admin/delete/")
    @PermissionCheck
    public String delete(@RequestParam Map<String , String> map) {
        int id = Integer.parseInt(map.get("id"));
        return userDeleteService.delete(id);
    }



}
