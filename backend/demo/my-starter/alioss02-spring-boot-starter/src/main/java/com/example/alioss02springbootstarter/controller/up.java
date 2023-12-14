package com.example.alioss02springbootstarter.controller;

import com.example.aliossspringbootstarter.util.AliossUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * @author ToTryEveryThing
 * @date 2023/12/14 9:23
 * @Description
 */
@RestController
@RequestMapping("images")
public class up {

    @Autowired
    AliossUtil aliossUtil;

    @PostMapping
    public String upload(MultipartFile file, @RequestParam("keyPrefix") String keyPrefix) throws Exception {
        return aliossUtil.uploadObject(file,keyPrefix);
    }

    @DeleteMapping("/{name}/{keyPrefix}")
    public String delete(@PathVariable String name, @PathVariable String keyPrefix){
        System.out.println(name);
        System.out.println(keyPrefix);
        return aliossUtil.deleteObject(name,keyPrefix);
    }

    @GetMapping
    public ArrayList<String> getList(String keyPrefix){
        return aliossUtil.getList(keyPrefix);
    }

}
