package com.example.article.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.constants.response.ApiResponse;

/**
* @author longlong
* @description 针对表【follow】的数据库操作Service
* @createDate 2023-07-03 16:29:35
*/
public interface FollowService{

    ApiResponse getStatus(Integer followerId, Integer followingId, String name);

    ApiResponse changeFollow(Integer followerId, Integer followingId, Boolean change, String name);

    ApiResponse getFollowingList(Integer id);

    ApiResponse getFollowerList(Integer id);
}
