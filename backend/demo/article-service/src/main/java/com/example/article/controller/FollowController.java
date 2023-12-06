package com.example.article.controller;



import com.example.article.service.impl.FollowServiceImpl;
import com.example.common.config.operationLog.MyLog;
import com.example.common.constants.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/follow")
public class FollowController {


    @Autowired
    private FollowServiceImpl followService;

    @GetMapping("/getstatus/")
    public ApiResponse getStatus(Integer followerId, Integer followingId, String name){
        return followService.getStatus(followerId,followingId, name);
    }

    @GetMapping("/changefollow/")
    @MyLog
    public ApiResponse changeFollow(Integer followerId,Integer followingId, Boolean change, String name){
        return followService.changeFollow(followerId,followingId,change,name);
    }


    @GetMapping("/getfollowinglist/")
    public ApiResponse getFollowingList(Integer id){
        return followService.getFollowingList(id);
    }


    @GetMapping("/getfollowerlist/")
    public ApiResponse getFollowerList(Integer id){
        return followService.getFollowerList(id);
    }


}
