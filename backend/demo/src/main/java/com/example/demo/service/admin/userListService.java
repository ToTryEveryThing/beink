package com.example.demo.service.admin;
import com.alibaba.fastjson.JSONObject;


public interface userListService {
    JSONObject userList(String name,Integer page);
}
