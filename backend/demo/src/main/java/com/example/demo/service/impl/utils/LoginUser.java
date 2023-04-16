package com.example.demo.service.impl.utils;

import com.alibaba.fastjson.annotation.JSONField;
import com.example.demo.pojo.user.web;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/***
 * @author 睡醒继续做梦
 * @date 2023/4/14 11:32
 */
@Data
@NoArgsConstructor
public class LoginUser implements UserDetails , Serializable {

    private web web;

    private List<String> permissions;

    public LoginUser(web web, List<String> permissions) {
        this.web = web;
        this.permissions = permissions;
        this.auth = new ArrayList<>();
    }
    @JSONField(serialize = false)
    private List<SimpleGrantedAuthority> auth;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(auth != null) return auth;
        for(String i : permissions){
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(i);
            auth.add(simpleGrantedAuthority);
        }
        return auth;
    }


    @Override
    public String getPassword() {
        return web.getPassword();
    }

    @Override
    public String getUsername() {
        return web.getAccount();
    }

    //todo 一系列都试试
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return web.getEnable();
    }
}
