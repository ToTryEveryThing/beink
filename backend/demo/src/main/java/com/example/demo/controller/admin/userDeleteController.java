package com.example.demo.controller.admin;


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
    private HttpServletRequest request;

    @Autowired
    private userDeleteService userDeleteService;
//+
    @PostMapping("/user/admin/delete/")
    public String delete(@RequestParam Map<String , String> map) throws Exception {
        if(!new IdandName().admin(request.getHeader("Authorization"),map.get("account"))){
            return "error";
        }
        int id = Integer.parseInt(map.get("id"));
        return userDeleteService.delete(id);
    }



}
