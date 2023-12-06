package com.example.user.service;

import com.example.common.constants.response.ApiResponse;

/**
 * @author 睡醒继续做梦
 * @date 2022/12/5
 */
public interface UserUpdateService {
    ApiResponse<String> Updata(int id, String account, String backimg, String role, Boolean isEnabled);

    ApiResponse update(String map);
}
