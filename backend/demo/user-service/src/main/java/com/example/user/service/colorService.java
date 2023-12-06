package com.example.user.service;


import com.example.common.constants.response.ApiResponse;

public interface colorService {
    ApiResponse<Void> setColor(String account, String color, String list);
}
