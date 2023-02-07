package com.example.demo.service.admin;

import com.example.demo.controller.common.Result;
import com.example.demo.pojo.web;

import java.util.List;


public interface userDeleteService {
    Result delete(Integer id);
    Result deleteMultiple(String s);
}
