package com.example.user.service;


import com.example.common.constants.response.ApiResponse;

import java.util.Map;

public interface LoginService {
    ApiResponse<Map<String, String>> getToken(String account, String password, String code, String base64);
}
