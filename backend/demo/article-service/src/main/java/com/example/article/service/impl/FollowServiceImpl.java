package com.example.article.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.article.mapper.FollowMapper;
import com.example.article.pojo.Follow;
import com.example.article.pojo.FollowUser;
import com.example.article.service.FollowService;
import com.example.common.constants.response.ApiResponse;
import com.example.common.mapper.WebMapper;
import com.example.common.pojo.web;
import com.sun.istack.internal.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
* @author longlong
* @description 针对表【follow】的数据库操作Service实现
* @createDate 2023-07-03 16:29:35
*/
@Service
public class FollowServiceImpl implements FollowService {

    @Autowired
    private FollowMapper followMapper;

    @Autowired
    private WebMapper webMapper;

    @Override
    public ApiResponse getStatus(Integer followerId, Integer followingId, String name) {

        if(followingId == null){
            followingId = NAMETOID(name);
        }

        QueryWrapper<Follow> followQueryWrapper = new QueryWrapper<>();
        followQueryWrapper.eq("follower_id",followerId).eq("following_id",followingId);
        Follow follow = followMapper.selectOne(followQueryWrapper);
        if(follow != null){
            return ApiResponse.success(1);
        }
        return ApiResponse.success(0);

    }

    @Override
    public ApiResponse changeFollow(Integer followerId, Integer followingId, Boolean change, String name) {

        if(followingId == null) followingId = NAMETOID(name);

        QueryWrapper<Follow> followQueryWrapper = new QueryWrapper<>();

        if(!change){
            //增加关系
            followQueryWrapper.eq("follower_id",followerId).eq("following_id",followingId);
            Follow follow = followMapper.selectOne(followQueryWrapper);
            if(follow != null){
                return ApiResponse.success(1);
            }
            Follow ff = new Follow(null, followerId, followingId);
            followMapper.insert(ff);
            System.out.println("增加");

        }else{
            //删除关系
            followQueryWrapper.eq("follower_id",followerId).eq("following_id",followingId);
            followMapper.delete(followQueryWrapper);
            System.out.println("删除");
        }

        return ApiResponse.success();


    }

    /**
     *  获取谁关注了该id
     * @param id
     * @return
     */
    @Override
    public ApiResponse getFollowingList(Integer id) {
        List<Follow> follows = followMapper.selectAllByFollowingId(id);
        return getApiResponse(follows, "ing");
    }


    /**
     * id关注了谁
     * @param id
     * @return
     */
    @Override
    public ApiResponse getFollowerList(Integer id) {
        List<Follow> follows = followMapper.selectAllByFollowerId(id);
        return getApiResponse(follows, "er");
    }

    @NotNull
    private ApiResponse getApiResponse(List<Follow> follows, String ffff) {
        List<FollowUser> list = new ArrayList<>();
        follows.forEach(follow ->{
            web web;
            if(ffff.equals("ing")) web = webMapper.selectById(follow.getFollowerId());
            else web = webMapper.selectById(follow.getFollowingId());
            FollowUser followUser = new FollowUser();
            BeanUtils.copyProperties(followUser,web);
            list.add(followUser);
        });
        return ApiResponse.success(list);
    }

    Integer NAMETOID(String name){
        QueryWrapper<web> q = new QueryWrapper<>();
        q.eq("account",name);
        web web = webMapper.selectOne(q);
        return web.getId();
    }


}




