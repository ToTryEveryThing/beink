package com.example.demo.service.impl.web;

import com.example.demo.controller.common.Result;
import com.example.demo.pojo.web;
import com.example.demo.service.impl.utils.UserDetailsImpl;
import com.example.demo.service.web.LoginService;
import com.example.demo.utils.Code.IsCode;
import com.example.demo.utils.JwtUtil;
import com.example.demo.utils.redisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.Map;

@Service
public class LoginImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;


    @Override
    public Result getToken(String account, String password) {


        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(account,password);
//    自动处理异常
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticate.getPrincipal();
        web web = loginUser.getWeb();
        String jwt = JwtUtil.createJWT(String.valueOf(web.getAccount()));
        Map<String,String> map = new HashMap<>();
        map.put("token",jwt);

        return new Result(1,"success",map);
    }
}
