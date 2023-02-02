package com.example.demo.service.admin;

import com.example.demo.controller.common.Result;

/**
 * @author 睡醒继续做梦
 * @date 2022/11/26
 */
public interface adminUtilsService {
    Result save(String markdown);
    String show();
    Result redisShow();
}
