package com.example.demo.service.impl.web;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.controller.common.ApiResponse;
import com.example.demo.pojo.Target;
import com.example.demo.service.web.TargetService;
import com.example.demo.mapper.TargetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author lenovo
* @description 针对表【target】的数据库操作Service实现
* @createDate 2023-09-07 11:13:37
*/
@Service
public class TargetServiceImpl implements TargetService{

    @Resource
    private TargetMapper targetMapper;

    @Override
    public ApiResponse<Void> addTarget(Target target) {
        targetMapper.insert(target);
        return ApiResponse.success();
    }

    @Override
    public ApiResponse<List<Target>> showTarget() {
        List<Target> targets = targetMapper.selectList(null);
        System.out.println(targetMapper.shofffw());
        targets.forEach(System.out::println);
        return ApiResponse.success(targets);
    }

    @Override
    public ApiResponse<Void> updateTarget(Target target) {
        targetMapper.updateById(target);
        return ApiResponse.success();
    }
}




