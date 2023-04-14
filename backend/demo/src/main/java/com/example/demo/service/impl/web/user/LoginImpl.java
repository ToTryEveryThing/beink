package com.example.demo.service.impl.web.user;

import com.example.demo.controller.common.Result;
import com.example.demo.pojo.user.web;
import com.example.demo.service.impl.utils.LoginUser;
import com.example.demo.service.web.user.LoginService;
import com.example.demo.utils.JwtUtil;
import com.example.demo.utils.redisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.demo.constants.radis.redisConstants.REDIS_JWT_TTL;
import static com.example.demo.constants.radis.redisConstants.REDIS_TOKEN;

@Service
public class LoginImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    redisUtil redisUtil;




    @Override
    public Result getToken(String account, String password) {

        /***
         * 调用 UserDetailServiceImpl
         * 进行认证
         */
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(account,password,new ArrayList<>());

//    自动处理异常
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();

        web web = loginUser.getWeb();
        String jwt = JwtUtil.createJWT(web.getAccount(), web.getRole());
        Map<String,String> map = new HashMap<>();

        map.put("token",jwt);

        //TODO 序列化 出问题了！！！！！
        redisUtil.set(REDIS_TOKEN + account, jwt, REDIS_JWT_TTL);

        return new Result(1,"success",map);
    }
}
