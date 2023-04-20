package com.example.demo.service.impl.web.user;

import com.example.demo.controller.common.ApiResponse;
import com.example.demo.pojo.user.web;
import com.example.demo.service.impl.utils.LoginUser;
import com.example.demo.service.web.user.LoginService;
import com.example.demo.utils.Code.IsCode;
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

import static com.example.demo.constants.radis.redisConstants.*;

@Service
public class LoginImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    redisUtil redisUtil;


    @Override
    public ApiResponse<Map<String, String>> getToken(String account, String password, String code, String base64) {

        /***
         * 调用 UserDetailServiceImpl
         * 进行认证
         */

        Boolean f = new IsCode().is(REDIS_CAPTCHA + code,base64,redisUtil);
        if(!f){
           return ApiResponse.error(0,"验证码错误");
        }

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(account,password,new ArrayList<>());

//    自动处理异常
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();

        web web = loginUser.getWeb();
        String jwt = JwtUtil.createJWT(web.getAccount(), web.getRole());
        Map<String,String> map = new HashMap<>();

        map.put("token",jwt);

        System.out.println("web.getEnable() = " + web.getEnable());

        redisUtil.set(REDIS_TOKEN + account, jwt, REDIS_JWT_TTL);

        return ApiResponse.success(map);
    }
}
