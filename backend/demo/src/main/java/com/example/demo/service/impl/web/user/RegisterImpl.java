package com.example.demo.service.impl.web.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.controller.common.Result;
import com.example.demo.mapper.article.PublicMapper;
import com.example.demo.mapper.user.WebMapper;
import com.example.demo.pojo.article.Public;
import com.example.demo.pojo.user.web;
import com.example.demo.service.web.user.RegisterService;
import com.example.demo.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class RegisterImpl implements RegisterService {

    @Autowired
    private PublicMapper publicMapper;

    @Autowired
    private WebMapper webMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 使用分布式锁 注册用户，可以保证在高并发的情况下 不会被多次注册
     * @param account
     * @param password
     * @return
     */
//    @Override
    public Result regasdgsdgister(String account, String password) {

        final String key = account + "-lock";
        final String value = System.nanoTime() + " " + UUID.randomUUID();
        ValueOperations v = stringRedisTemplate.opsForValue();
        /**  setIfAbsent
         * 如果为空就set值，并返回1
         * 如果存在(不为空)不进行操作，并返回0
         */
        Boolean res = v.setIfAbsent(key,value);
        if(res){

            stringRedisTemplate.expire(key,20L, TimeUnit.SECONDS);



            if(account == null){
                return new Result(0,"用户名不能为空");
            }
            if(password.length() == 0){
                return new Result(0,"密码不能为空");
            }

            account = account.trim();
            if(account.length()==0){
                return new Result(0,"用户名不能为空");
            }

            if(password.length()>50){
                return new Result(0,"密码长度不能超过50");
            }

            QueryWrapper<web> queryWrapper = new QueryWrapper<web>();
            queryWrapper.eq("account",account);
            List<web> webs =  webMapper.selectList(queryWrapper);
            if(!webs.isEmpty()){
                return new Result(0,"用户名已存在");
            }
            String pass = passwordEncoder.encode(password);

            String jwt = JwtUtil.createJWT(account);
            web web1 = new web();
            web1.setBackimg("5");
            web1.setList("[]");
            web1.setPassword(pass);
            web1.setAccount(account);
            web1.setDate(new Date());
            webMapper.insert(web1);
            String git  = "[{\"title\":\"你好\",\"name\":1,\"content\":\"## new content\",\"show\":true}]\n" +
                    "\n";
            String title = "[\"你好\"]";
            System.out.println(publicMapper.insert(new Public(account, title,git))+999999);
            System.out.println(new Date());
            if(value.equals(v.get(key).toString())){
                stringRedisTemplate.delete(key);
            }
            return new Result(1,"success",jwt);
        }

        return null;
    }


    @Override
    public Result register(String account, String password) {

        if(account == null){
            return new Result(0,"用户名不能为空");
        }
        if(password.length() == 0){
            return new Result(0,"密码不能为空");
        }

        account = account.trim();
        if(account.length()==0){
            return new Result(0,"用户名不能为空");
        }

        if(password.length()>50){
            return new Result(0,"密码长度不能超过50");
        }

        QueryWrapper<web> queryWrapper = new QueryWrapper<web>();
        queryWrapper.eq("account",account);
        List<web> webs =  webMapper.selectList(queryWrapper);
        if(!webs.isEmpty()){
            return new Result(0,"用户名已存在");
        }
        String pass = passwordEncoder.encode(password);

        String jwt = JwtUtil.createJWT(account);
        web web1 = new web();
        web1.setBackimg("5");
        web1.setList("[]");
        web1.setPassword(pass);
        web1.setAccount(account);
        web1.setDate(new Date());
        webMapper.insert(web1);
        String git  = "[{\"title\":\"你好\",\"name\":1,\"content\":\"## new content\",\"show\":true}]\n" +
                "\n";
        String title = "[\"你好\"]";
        System.out.println(publicMapper.insert(new Public(account, title,git))+999999);
        System.out.println(new Date());
        return new Result(1,"success",jwt);
    }




}