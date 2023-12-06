package com.example.user.service;


import com.example.common.constants.response.ApiResponse;

public interface UserDeleteService {
    ApiResponse<Void> delete(Integer id);
    ApiResponse<Void> deleteMultiple(String s);
}
