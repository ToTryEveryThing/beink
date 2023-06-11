package com.example.demo.service.admin;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.controller.common.ApiResponse;

/***
 * @author 睡醒继续做梦
 * @date 2023/4/30 11:51
 */
public interface getOperationLog {

    public JSONObject getLog(Integer page, String ip, String result);

    ApiResponse<Void> de(String result);
}
