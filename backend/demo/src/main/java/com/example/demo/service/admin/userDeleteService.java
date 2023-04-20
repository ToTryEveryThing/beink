package com.example.demo.service.admin;

import com.example.demo.controller.common.ApiResponse;


public interface userDeleteService {
    ApiResponse<Void> delete(Integer id);
    ApiResponse<Void> deleteMultiple(String s);
}
