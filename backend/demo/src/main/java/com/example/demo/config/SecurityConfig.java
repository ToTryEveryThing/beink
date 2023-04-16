package com.example.demo.config;


import com.example.demo.config.filter.JwtAuthenticationTokenFilter;
import com.example.demo.exception.filterController.AccessDeniedHandlerImpl;
import com.example.demo.exception.filterController.AuthenticationEntryImpl;
import com.example.demo.service.impl.utils.UserDetailServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.example.demo.constants.controller.controller.patterns;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)//开启全局的细粒度方法级别权限控制功能
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private UserDetailServiceImpl DetailService;

    @Autowired
    AuthenticationEntryImpl authenticationEntry;

    @Autowired
    AccessDeniedHandlerImpl accessDeniedHandler;


    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(DetailService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                    .antMatchers(patterns).permitAll()
                    .antMatchers("/**/admin/**/").hasRole("admin")
                    .antMatchers("/admin/test/").hasRole("admin")
                    //.anonymous()匿名访问  登录状态下 不能访问
                    .antMatchers(HttpMethod.OPTIONS).permitAll()
                    .anyRequest().authenticated();
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        //        http.exceptionHandling()
//                .authenticationEntryPoint(authenticationEntry)//认证失败处理器
//                .accessDeniedHandler(accessDeniedHandler);//权限不足处理器
//        跨域
        http.cors();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/websocket/**");
        web.ignoring().antMatchers("/GuessWebsocket/**");
    }

}