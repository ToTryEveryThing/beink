package com.example.demo.service.web.user;

import com.example.demo.controller.common.ApiResponse;

public interface colorService {
    ApiResponse<Void> setColor(String account, String color, String list);
}
