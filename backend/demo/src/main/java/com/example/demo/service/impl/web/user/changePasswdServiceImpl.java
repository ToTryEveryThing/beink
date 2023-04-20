package com.example.demo.service.impl.web.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.demo.controller.common.ApiResponse;
import com.example.demo.controller.common.Result;
import com.example.demo.mapper.user.WebMapper;
import com.example.demo.pojo.user.web;
import com.example.demo.service.web.user.changePasswdService;
import com.example.demo.utils.redisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.example.demo.constants.radis.redisConstants.REDIS_TOKEN;

/***
 * @author 睡醒继续做梦
 * @date 2023/4/20 9:12
 */

@Service
public class changePasswdServiceImpl implements changePasswdService {

    @Autowired
    WebMapper webMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    redisUtil redisUtil;


    @Override
    public ApiResponse<Void> change(String name, String Old, String New) {

        LambdaQueryWrapper<web> q = new LambdaQueryWrapper<>();
        q.eq(web::getAccount, name);
        web web = webMapper.selectOne(q);
        if(web != null){
            if(!passwordEncoder.matches(Old, web.getPassword())){
                return ApiResponse.error(0,"密码错误");
            }
            web.setPassword(passwordEncoder.encode(New));
            int i = webMapper.updateById(web);
            if(i == 1){
                redisUtil.del(REDIS_TOKEN + name);
                return ApiResponse.error(1,"修改成功");
            } return ApiResponse.error(0,"修改失败");
        }
        return ApiResponse.error(0, "账号不存在");
    }
}
