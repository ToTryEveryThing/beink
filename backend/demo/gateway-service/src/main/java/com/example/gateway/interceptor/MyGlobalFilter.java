package com.example.gateway.interceptor;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.common.constants.response.ApiResponse;
import com.example.common.exception.controllerException.TokenException;
import com.example.common.mapper.WebMapper;
import com.example.common.pojo.web;
import com.example.common.utils.JwtUtil;
import com.example.common.utils.redisUtil;
import io.jsonwebtoken.Claims;
import lombok.SneakyThrows;
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

    @Autowired
    private redisUtil redisUtil;

    @SneakyThrows
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {


        ServerHttpResponse response = exchange.getResponse();

        ServerHttpRequest request = exchange.getRequest();

        String path = request.getPath().toString();

        //放行
        if(isExclude(path)){
            return chain.filter(exchange);
        }
        String token = null;

        List<String> authorization = request.getHeaders().get("Authorization");
        if(authorization != null && !authorization.isEmpty()){
            token = authorization.get(0);
            token = token.substring(7);
        }else{
            System.out.println("token为空");
            String responseBody = "{\n" +
                    "    \"code\":403,\n" +
                    "    \"message\": \"登录后操作\"\n" +
                    "}";
            return response.writeWith(Mono.just(response.bufferFactory().wrap(responseBody.getBytes())));
        }



        System.out.println(token);
        try {
            Claims claims = jwtUtil.parseJWT(token);
            System.out.println("claims.get(\"role\") = " + claims.get("role"));
            String username = claims.getSubject();

            if(path.contains("admin") && claims.get("role").equals("user")){
                String responseBody = "{\n" +
                        "    \"code\":403,\n" +
                        "    \"message\": \"权限不足\"\n" +
                        "}";
                return response.writeWith(Mono.just(response.bufferFactory().wrap(responseBody.getBytes())));
            }

            LambdaQueryWrapper<web> q = new LambdaQueryWrapper<>();
            q.eq(web::getAccount,username);
            web web = webMapper.selectOne(q);
            if(web == null) {
                System.out.println("用户不存在");
                String responseBody = "{\n" +
                        "    \"code\":500,\n" +
                        "    \"message\": \"用户不存在\"\n" +
                        "}";
                return response.writeWith(Mono.just(response.bufferFactory().wrap(responseBody.getBytes())));
            }
            System.out.println("用户存在");
            //用户名放在请求头中
            ServerWebExchange build = exchange.mutate()
                    .request(builder -> builder.header("user-info", username))
                    .build();
            return chain.filter(build);
        } catch (Exception e) {
            String responseBody = "{\n" +
                    "    \"code\":500,\n" +
                    "    \"message\": \"操作异常\"\n" +
                    "}";
            return response.writeWith(Mono.just(response.bufferFactory().wrap(responseBody.getBytes())));
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
