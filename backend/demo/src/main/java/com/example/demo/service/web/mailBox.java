package com.example.demo.service.web;

import com.example.demo.controller.common.ApiResponse;

/***
 * @author 睡醒继续做梦
 * @date 2023/3/1 17:06
 */
public interface mailBox {
    public ApiResponse<Void> bind(String account, String mail, String code);


}
