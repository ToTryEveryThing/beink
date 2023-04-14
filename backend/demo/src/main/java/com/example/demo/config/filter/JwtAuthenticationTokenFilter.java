package com.example.demo.config.filter;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.mapper.user.WebMapper;
import com.example.demo.pojo.user.web;
import com.example.demo.utils.JwtUtil;
import com.example.demo.utils.redisUtil;
import io.jsonwebtoken.Claims;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.demo.constants.radis.redisConstants.REDIS_TOKEN;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter  {

    @Autowired
    private WebMapper userMapper;

    @Autowired
    redisUtil redisUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain)
            throws ServletException, IOException{
        String token = request.getHeader("Authorization");
//        判断为空
        if (!StringUtils.hasText(token) || !token.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        token = token.substring(7);


        String userid;
        web user;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userid = claims.getSubject();
            if (!redisUtil.hasKey(REDIS_TOKEN + userid))
                throw new RuntimeException("请重新登录");
            QueryWrapper<web> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("account",userid);
            user = userMapper.selectOne(queryWrapper);
            if(user == null)
                throw new RuntimeException("请重新登录");
        } catch (Exception e) {
            throw new RuntimeException("请重新登录");
        }

//        存入SecurityContextHolder
        try {
            SecurityContextHolder.getContext().setAuthentication(new JwtUtil().getRoleList(token));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        filterChain.doFilter(request, response);
    }
}