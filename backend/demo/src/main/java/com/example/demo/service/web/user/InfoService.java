package com.example.demo.service.web.user;


import com.example.demo.controller.common.ApiResponse;

import java.util.Map;


public interface InfoService {
      ApiResponse<Map<String, String>> getinfo();
}
