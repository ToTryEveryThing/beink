package com.example.demo.service.web;

import com.example.demo.controller.common.Result;

import java.util.Map;

public interface RegisterService {
   Result register(String account, String password);
}
