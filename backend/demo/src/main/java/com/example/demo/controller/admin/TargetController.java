package com.example.demo.controller.admin;

import com.example.demo.controller.common.ApiResponse;
import com.example.demo.pojo.Target;
import com.example.demo.service.web.TargetService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ToTryEveryThing
 * @date 2023/9/7 11:35
 */
@RestController
public class TargetController {

    @Resource
    private TargetService targetService;


    @PostMapping("/admin/target/add/")
    public ApiResponse<Void> addTarget(@RequestBody Target target){
        return targetService.addTarget(target);
    }

    @GetMapping("/admin/target/show/")
    public ApiResponse<List<Target>> showTarget(){
        return targetService.showTarget();
    }

    @PostMapping("/admin/target/update/")
    public ApiResponse<Void> updateTarget(@RequestBody Target target){return targetService.updateTarget(target);}

}
