package com.example.user.service;
import com.alibaba.fastjson.JSONObject;


public interface UserListService {
    JSONObject userList(String name,Integer page);
}
