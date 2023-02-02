package com.example.demo.service.impl.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.controller.common.Result;
import com.example.demo.mapper.PublicMapper;
import com.example.demo.pojo.Public;
import com.example.demo.service.admin.adminUtilsService;
import com.example.demo.utils.redisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 睡醒继续做梦
 * @date 2022/11/26
 */
@Service
public class adminUtilsImpl implements adminUtilsService {

    @Autowired
    private redisUtil redisUtil;


    @Autowired
    private PublicMapper publicMapper;

    @Override
    public Result save(String markdown) {

        QueryWrapper<Public> q = new QueryWrapper<>();
        Public aPublic = new Public();
        aPublic.setGit(markdown);
        q.eq("id",1);
        int update = publicMapper.update(aPublic, q);
        if(update>=1){
            redisUtil.set("markdown",markdown);
            return new Result(1,"success");
        }
        else return new Result(0,"error");
    }

    @Override
    public Result redisShow() {
        if(redisUtil.get("markdown")==null){
            redisUtil.set("markdown",this.show());
        }
        return new Result(1,"success",redisUtil.get("markdown"));
    }

    @Override
    public String show() {
        Public Public = publicMapper.selectById(1);
        return Public.getGit();
    }
}
