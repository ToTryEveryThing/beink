package com.example.demo;

import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.mapper.webMapper;
import com.example.demo.pojo.user;
import com.example.demo.pojo.web;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

@SpringBootTest

class DemoApplicationTests {

    @Autowired
    private UserMapper UserMapper;

    @Autowired
    private webMapper webMapper;

    @Test
    void dd(){
        QueryWrapper<user> u = new QueryWrapper<>();
        String res;
        user user = UserMapper.selectById(1);
        System.out.println(user.getPassword());
    }


    @Test
    void contextLoads() {
        List<user> users = UserMapper.selectList(null);
        users.forEach(System.out::println);
    }

    @Test
    void TestInsert(){
        user user = new user();
        user.setId(880);
        user.setAccount("天天学习");
        user.setPassword("hhh");

        int res = UserMapper.insert(user);
        System.out.println(res);
        System.out.println(new Date());
    }

    @Test
    void TestDelete(){
//        一：
//        int res = UserMapper.deleteById(1);
//        System.out.println(res);

//        二:
//        delete from user where account = ? and password = ?
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("account","jkl");
//        map.put("password","123root");
//        UserMapper.deleteByMap(map);

//        三:
//        删除多个
//        DELETE FROM user WHERE id IN ( ? , ? )
            List<Integer> list = Arrays.asList(80,880);
            UserMapper.deleteBatchIds(list);
    }
    @Test
    void TestUpdata(){
        UpdateWrapper<user> q = new UpdateWrapper<>();
        q.eq("id",1);
        user user = new user();
        user.setAccount("sdfhsdf");
        UserMapper.update(user, q);
    }

    @Test
//    加密测试
    void passwordEncode(){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("666"));
    }


    @Test
    void TestSelect(){
        QueryWrapper<web> queryWrapper = new QueryWrapper<>();

        queryWrapper.like("backimg","6").orderByDesc("id");
        List<web> web = webMapper.selectList(queryWrapper);
        web.forEach(System.out::println);
//        通过id查询信息
//        user user = UserMapper.selectById(2);
//        System.out.println(user.toString().isEmpty());

//        通过多个id进行查询
//        List<Integer> list = Arrays.asList(3,2);
//        List<user> users = UserMapper.selectBatchIds(list);
//        users.forEach(System.out::println);

//        通过map查询
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("account",666);
//        List<user> users = UserMapper.selectByMap(map);
//        users.forEach(System.out::println);

//      所有的数据
//        List<user> users = UserMapper.selectList(null);
//        users.forEach(System.out::println);


//        自定义的查询
//        Map<String, Object> map = UserMapper.selectMapById(2);
//        System.out.println(map);
    }

}
