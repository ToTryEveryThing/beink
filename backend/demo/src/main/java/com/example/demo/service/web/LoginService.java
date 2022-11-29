package com.example.demo.service.web;

import java.util.Map;

public interface LoginService {
     Map<String,String> getToken(String account, String password);
}
