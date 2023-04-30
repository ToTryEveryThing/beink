package com.example.demo.service.impl.admin;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.controller.common.ApiResponse;
import com.example.demo.mapper.LogMapper;
import com.example.demo.pojo.Log;
import com.example.demo.service.admin.getOperationLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/***
 * @author 睡醒继续做梦
 * @date 2023/4/30 11:52
 */
@Service
public class getOperationLogService implements getOperationLog {

    @Autowired
    private LogMapper logMapper;


    @Override
    public JSONObject getLog(Integer page) {

        IPage<Log> logIPage = new Page<>(page,10);

        QueryWrapper<Log> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("result");
        JSONObject jsonObject = new JSONObject();
        List<Log> records = logMapper.selectPage(logIPage, queryWrapper).getRecords();
        jsonObject.put("code", 200);
        jsonObject.put("data", records);
        jsonObject.put("count", logMapper.selectCount(queryWrapper));
        return jsonObject;

    }
}
