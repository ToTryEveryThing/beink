package com.example.demo.service.web;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.controller.common.ApiResponse;

import java.util.Map;

/**
 * @author 睡醒继续做梦
 * @date 2022/12/5
 */
public interface updataService {
    ApiResponse<String> Updata(int id, String account, String backimg, String role, Boolean isEnabled);

    ApiResponse update(String map);
}
