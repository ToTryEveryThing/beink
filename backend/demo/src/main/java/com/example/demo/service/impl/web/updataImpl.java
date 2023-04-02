package com.example.demo.service.impl.web;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.demo.controller.common.Result;
import com.example.demo.mapper.WebMapper;
import com.example.demo.pojo.user.web;
import com.example.demo.service.web.updataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 睡醒继续做梦
 * @date 2022/12/5
 */
@Service
public class updataImpl implements updataService {

    @Autowired
    private WebMapper webMapper;

    @Override
    public Result Updata(int id, String account, String backimg, String role) {
        web web = new web();
        web.setAccount(account);
        web.setBackimg(backimg);
        web.setRole(role);
        UpdateWrapper<web> q = new UpdateWrapper<>();
        q.eq("id",id);
//        不屑password 不被修改
        int update = webMapper.update(web, q);
        if(update>=1){
            return new Result(1,"true");
        }else return new Result(0,"false");
    }
}
