package com.example.demo.service.web.user;

import com.example.demo.controller.common.ApiResponse;

import java.util.Map;

public interface LoginService {
    ApiResponse<Map<String, String>> getToken(String account, String password, String code, String base64);
}
