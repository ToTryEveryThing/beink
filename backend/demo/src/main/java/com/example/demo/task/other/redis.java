package com.example.demo.task.other;

import com.example.demo.utils.redisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static com.example.demo.constants.radis.redisConstants.*;

/***
 * @author 睡醒继续做梦
 * @date 2023/5/11 14:22
 */
@Component
public class redis {
    private boolean hasExecuted = false;

    @Autowired
    private redisUtil redisUtil;

    @PostConstruct
    public void init() {
        if (!hasExecuted) {
            // 这里编写需要在项目启动时执行的代码
            redisUtil.hset(REDIS_LIMIT, REDIS_REGISTER, true);
            redisUtil.hset(REDIS_LIMIT, REDIS_CHAT, true);
            // 将标志位置为已执行
            hasExecuted = true;
        }
    }
}
