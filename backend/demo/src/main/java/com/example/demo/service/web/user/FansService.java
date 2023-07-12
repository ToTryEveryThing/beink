package com.example.demo.service.web.user;

import com.example.demo.controller.common.ApiResponse;
import com.example.demo.pojo.user.Fans;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author longlong
* @description 针对表【fans】的数据库操作Service
* @createDate 2023-07-03 13:54:55
*/
public interface FansService {

    void  addRelationship(String name,String title,Integer authorId, Integer articleId);

}
