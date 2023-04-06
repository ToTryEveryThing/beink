package com.example.demo.service.impl.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.controller.common.Result;
import com.example.demo.mapper.article.PublicMapper;
import com.example.demo.pojo.article.Public;
import com.example.demo.service.admin.adminUtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 睡醒继续做梦
 * @date 2022/11/26
 */
@Service
public class adminUtilsImpl implements adminUtilsService {

    @Autowired
    private PublicMapper publicMapper;

    @Override
    public Result save(String markdown, String name,String title) {

        QueryWrapper<Public> q = new QueryWrapper<>();
        q.eq("name",name);
        Public aPublic = new Public();
        aPublic.setGit(markdown);
        aPublic.setName(name);
        System.out.println(title);
        if(title.length()<=2)title="";
        aPublic.setTitle(title);
        int update = publicMapper.update(aPublic,q);
        if(update>=1){
            return new Result(1,"success");
        }
        else return new Result(0,"error");
    }

    @Override
    public Result show(String name) {
        QueryWrapper<Public> q = new QueryWrapper<>();
        q.eq("name",name);
        Public Public = publicMapper.selectOne(q);
        if(Public!=null){
            return new Result(1,"success",Public);
        }
        return new Result(0,"error");
    }

    @Override
    public Result showAll() {
        QueryWrapper<Public> q = new QueryWrapper<>();
        q.isNotNull("title");
        q.isNotNull("git");
        List<Public> publics = publicMapper.selectList(q);
        for(Public i : publics){
            i.setGit("");
        }
        return new Result(1,"success",publics);

    }
}
