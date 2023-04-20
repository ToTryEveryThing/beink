package com.example.demo.service.web.user;

import com.example.demo.controller.common.ApiResponse;

public interface RegisterService {
   ApiResponse<String> register(String account, String password, String code, String base64);
}
