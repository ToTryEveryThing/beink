package com.example.mail.service;


import com.example.common.constants.response.ApiResponse;

/***
 * @author 睡醒继续做梦
 * @date 2023/3/1 17:06
 */
public interface MailBindService {
    public ApiResponse<Void> bind(String account, String mail, String code);


}
