package com.example.user.service;

import com.example.common.constants.response.ApiResponse;

import java.util.Map;


public interface InfoService {
      ApiResponse<Map<String, String>> getinfo();
}
