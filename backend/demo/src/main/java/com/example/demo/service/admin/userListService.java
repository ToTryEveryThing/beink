package com.example.demo.service.admin;

import com.example.demo.pojo.web;

import java.util.List;

public interface userListService {
    List<web> userList(String account);
}
