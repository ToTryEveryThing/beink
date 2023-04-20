package com.example.demo.controller.admin;


import com.example.demo.controller.common.ApiResponse;
import com.example.demo.controller.common.Result;
import com.example.demo.service.admin.userDeleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.Map;

@RestController
public class userDeleteController {

    @Autowired
    private userDeleteService userDeleteService;
//+
    @PostMapping("/user/admin/delete/")
    public ApiResponse<Void> delete(@RequestParam Map<String , String> map) {
        int id = Integer.parseInt(map.get("id"));
        return userDeleteService.delete(id);
    }

    @PostMapping("/user/admin/multiple/")
    public ApiResponse<Void> DeleteMultiple(@RequestParam String ids){
        System.out.println(ids);
        return userDeleteService.deleteMultiple(ids);
    }



}
