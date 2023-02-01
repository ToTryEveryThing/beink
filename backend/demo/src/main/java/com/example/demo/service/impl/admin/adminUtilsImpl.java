package com.example.demo.service.impl.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
    public String save(String markdown) {

        QueryWrapper<Public> q = new QueryWrapper<>();
        Public aPublic = new Public();
        aPublic.setGit(markdown);
        q.eq("id",1);
        int update = publicMapper.update(aPublic, q);
        if(update>=1){
            redisUtil.set("markdown",markdown);
            return "success";
        }
        else return "error";
    }

    @Override
    public String redisShow() {
        if(redisUtil.get("markdown")==null){
            redisUtil.set("markdown",this.show());
        }
        return (String) redisUtil.get("markdown");
    }

    @Override
    public String show() {
        Public Public = publicMapper.selectById(1);
        return Public.getGit();
    }

    @Override
    public String save_list(String backlist) {
        QueryWrapper<Public> q = new QueryWrapper<>();
        Public aPublic = new Public();
        aPublic.setBacklist(backlist);
        q.eq("id",1);
        int update = publicMapper.update(aPublic, q);
        if(update>=1)return "success";
        else return "error";
    }

    @Override
    public String show_list() {
        Public Public = publicMapper.selectById(1);
        return Public.getBacklist();
    }
}
