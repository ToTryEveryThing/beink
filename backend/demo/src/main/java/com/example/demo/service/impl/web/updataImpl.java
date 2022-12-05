package com.example.demo.service.impl.web;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.demo.mapper.webMapper;
import com.example.demo.pojo.user;
import com.example.demo.pojo.web;
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
    private webMapper webMapper;

    @Override
    public String Updata(int id, String account, String backimg) {
        web web = new web();
        web.setAccount(account);
        web.setBackimg(backimg);
        UpdateWrapper<web> q = new UpdateWrapper<>();
        q.eq("id",id);
//        不屑password 不被修改
        int update = webMapper.update(web, q);
        if(update>=1){
            return "true";
        }else return "false";
    }
}
