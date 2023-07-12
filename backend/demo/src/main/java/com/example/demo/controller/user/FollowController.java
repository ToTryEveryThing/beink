package com.example.demo.controller.user;


import com.example.demo.controller.common.ApiResponse;
import com.example.demo.service.impl.web.user.FollowServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FollowController {


    @Autowired
    private FollowServiceImpl followService;

    @GetMapping("/follow/getstatus/")
    public ApiResponse getStatus(Integer followerId,Integer followingId, String name){
        return followService.getStatus(followerId,followingId, name);
    }

    @GetMapping("/follow/changefollow/")
    public ApiResponse changeFollow(Integer followerId,Integer followingId, Boolean change, String name){
        return followService.changeFollow(followerId,followingId,change,name);
    }

    @GetMapping("/follow/getfollowinglist/")
    public ApiResponse getFansList(Integer id){
        return followService.getFansList(id);
    }

    @GetMapping("/follow/getfollowerlist/")
    public ApiResponse getFollowerList(Integer id){
        return followService.getFollowerList(id);
    }


}
