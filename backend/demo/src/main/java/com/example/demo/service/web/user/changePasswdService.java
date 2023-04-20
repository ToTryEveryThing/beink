package com.example.demo.service.web.user;

import com.example.demo.controller.common.ApiResponse;
import com.example.demo.controller.common.Result;

/***
 * @author 睡醒继续做梦
 * @date 2023/4/20 9:12
 */
public interface changePasswdService {

    public ApiResponse<Void> change(String name, String Old, String New);

}
