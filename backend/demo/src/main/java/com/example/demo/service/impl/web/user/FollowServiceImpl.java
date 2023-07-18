package com.example.demo.service.impl.web.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.controller.common.ApiResponse;
import com.example.demo.mapper.user.WebMapper;
import com.example.demo.pojo.Follow;
import com.example.demo.pojo.user.web;
import com.example.demo.service.web.user.FollowService;
import com.example.demo.mapper.user.FollowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
* @author longlong
* @description 针对表【follow】的数据库操作Service实现
* @createDate 2023-07-03 16:29:35
*/
@Service
public class FollowServiceImpl implements FollowService{

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
        List<web> list = new ArrayList<>();
        follows.forEach(follow ->{
            list.add(webMapper.selectById(follow.getFollowerId()));
        });
        return ApiResponse.success(list);
    }


    /**
     * id关注了谁
     * @param id
     * @return
     */
    @Override
    public ApiResponse getFollowerList(Integer id) {
        List<Follow> follows = followMapper.selectAllByFollowerId(id);
        List<web> list = new ArrayList<>();
        follows.forEach(follow ->{
            list.add(webMapper.selectById(follow.getFollowingId()));
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




