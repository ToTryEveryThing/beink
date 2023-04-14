package com.example.demo.service.impl.web.user;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.demo.controller.common.Result;
import com.example.demo.mapper.user.WebMapper;
import com.example.demo.pojo.user.web;
import com.example.demo.service.web.updataService;
import com.example.demo.utils.redisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.constants.radis.redisConstants.REDIS_TOKEN;

/**
 * @author 睡醒继续做梦
 * @date 2022/12/5
 */
@Service
public class updataImpl implements updataService {

    @Autowired
    private WebMapper webMapper;

    @Autowired
    redisUtil redisUtil;

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
            redisUtil.del(REDIS_TOKEN + account);
            return new Result(1,"true");
        }else return new Result(0,"false");
    }
}
