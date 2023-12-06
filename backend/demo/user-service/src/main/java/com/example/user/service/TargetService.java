package com.example.user.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.constants.response.ApiResponse;
import com.example.user.domain.Target;

import java.util.List;

/**
* @author lenovo
* @description 针对表【target】的数据库操作Service
* @createDate 2023-09-07 11:13:37
*/
public interface TargetService {

    public ApiResponse<Void> addTarget(Target target);

    ApiResponse<List<Target>> showTarget();

    ApiResponse<Void> updateTarget(Target target);
}
