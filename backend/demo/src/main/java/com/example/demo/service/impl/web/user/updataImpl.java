package com.example.demo.service.impl.web.user;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.demo.controller.common.ApiResponse;
import com.example.demo.mapper.user.WebMapper;
import com.example.demo.pojo.user.web;
import com.example.demo.service.web.updataService;
import com.example.demo.utils.redisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ApiResponse<String> Updata(int id, String account, String backimg, String role, Boolean isEnabled) {
        web web = new web();
        web.setAccount(account);
        web.setBackimg(backimg);
        web.setRole(role);
        web.setEnable(isEnabled);
        System.out.println(web);
        UpdateWrapper<web> q = new UpdateWrapper<>();
        q.eq("id",id);
//        不屑password 不被修改
        int update = webMapper.update(web, q);
        if(update>=1){
            if(!isEnabled) redisUtil.del(REDIS_TOKEN + account);
            return ApiResponse.success("true");
        }else return ApiResponse.error(0,"false");
    }

    @Override
    public ApiResponse update(String map) {

        System.out.println(map);

        JSONObject jsonObject = JSONObject.parseObject(map);

        //TODO email 名字  麻烦 暂时不改
        // 改完 用户名 所属文章没了。。。
        String email = jsonObject.getString("email");
        String account = jsonObject.getString("name");
        String userimage = jsonObject.getString("image");
        String oldpassword = jsonObject.getString("oldpassword");
        String newpassword = jsonObject.getString("newpassword");
        Integer id = Integer.valueOf(jsonObject.getString("id"));

        web web = new web();
        web.setUserimage(userimage);
        web.setId(id);
//        web.setAccount(account);


        if(account == null){
            return ApiResponse.error(0,"用户名不能为空");
        }
        account = account.trim();
        if(account.length()==0){
            return ApiResponse.error(0,"用户名不能为空");
        }

        QueryWrapper<web> queryWrapper = new QueryWrapper<web>();
        queryWrapper.eq("account",account);
        List<web> webs =  webMapper.selectList(queryWrapper);
        if(!webs.isEmpty()){
            if(!webs.get(0).getAccount().equals(account))
            return ApiResponse.error(0,"用户名已存在");
        }

        web web1 = webMapper.selectById(id);
        if(web1 != null){
            if(!passwordEncoder.matches(oldpassword, web1.getPassword())){
                return ApiResponse.error(0,"密码错误");
            }

            if(passwordEncoder.matches(newpassword, web1.getPassword())){
                return ApiResponse.error(0,"新旧密码一样");
            }
        }

        web.setPassword(passwordEncoder.encode(newpassword));
        webMapper.updateById(web);



        return ApiResponse.success();
    }



}
