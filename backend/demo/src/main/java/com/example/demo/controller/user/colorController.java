package com.example.demo.controller.user;

import com.example.demo.service.web.colorService;
import com.example.demo.utils.IdandName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class colorController {

    @Autowired
    private colorService colorService;

    @Autowired
    private HttpServletRequest request;

    @PostMapping("/user/account/color/")
    public Map<String,String> setColor(@RequestParam Map<String,String>map) throws Exception {
        String account = map.get("account");
        String color = map.get("backImg");
        String list = map.get("list");
        if(!new IdandName().user(request.getHeader("Authorization"),account)){
            return null;
        }
        return colorService.setColor(account,color,list);

    }
}
