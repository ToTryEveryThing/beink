package com.example.demo.service.admin;

import com.example.demo.controller.common.Result;


public interface userDeleteService {
    Result delete(Integer id);
    Result deleteMultiple(String s);
}
