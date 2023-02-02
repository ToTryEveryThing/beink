package com.example.demo.service.web;

import com.example.demo.controller.common.Result;

import java.util.Map;

public interface LoginService {
    Result getToken(String account, String password);
}
