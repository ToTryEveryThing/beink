package com.example.user.service;


import com.example.common.constants.response.ApiResponse;

public interface RegisterService {
   ApiResponse<String> register(String account, String password, String code);
}
