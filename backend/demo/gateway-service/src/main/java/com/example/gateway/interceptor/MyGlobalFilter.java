package com.example.gateway.interceptor;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.common.constants.response.ApiResponse;
import com.example.common.mapper.WebMapper;
import com.example.common.pojo.web;
import com.example.common.utils.JwtUtil;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.List;

import static com.example.common.constants.controller.controller.patterns;

/**
 * @author ToTryEveryThing
 * @date 2023/12/6 14:23
 * @Description
 */
@Component
@Configuration
public class MyGlobalFilter  implements GlobalFilter, Ordered {

    @Resource
    private JwtUtil jwtUtil;

    @Autowired
    private WebMapper webMapper;



    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {


        ServerHttpRequest request = exchange.getRequest();
        //放行
        if(isExclude(request.getPath().toString())){
            return chain.filter(exchange);
        }
        String token = null;


        ServerHttpResponse response = exchange.getResponse();

        List<String> authorization = request.getHeaders().get("Authorization");
        if(authorization != null && !authorization.isEmpty()){
            token = authorization.get(0);
            token = token.substring(7);
        }else{
            System.out.println("token为空");
            response.setStatusCode(org.springframework.http.HttpStatus.valueOf(HttpStatus.SC_UNAUTHORIZED));
            return response.setComplete();
        }

        System.out.println(token);
        try {
            String username = jwtUtil.parseJWT(token).getSubject();
            LambdaQueryWrapper<web> q = new LambdaQueryWrapper<>();
            q.eq(web::getAccount,username);
            web web = webMapper.selectOne(q);
            if(web == null) {
                System.out.println("用户不存在");
                response.setStatusCode(org.springframework.http.HttpStatus.valueOf(HttpStatus.SC_UNAUTHORIZED));
                return response.setComplete();
            }
            System.out.println("用户存在");
            //用户名放在请求头中
            ServerWebExchange build = exchange.mutate()
                    .request(builder -> builder.header("user-info", username))
                    .build();
            return chain.filter(build);
        } catch (Exception e) {
            response.setStatusCode(org.springframework.http.HttpStatus.valueOf(HttpStatus.SC_UNAUTHORIZED));
            return response.setComplete();
        }
    }

    private boolean isExclude(String path) {

        for (String pattern : patterns) {
            if(path.equals(pattern))return true;
        }

        return false;
    }


    /**
     * 设置权重，优先执行
     * @return
     */
    @Override
    public int getOrder(){
        return 0;
    }

}
