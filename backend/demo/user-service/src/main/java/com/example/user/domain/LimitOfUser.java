package com.example.user.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/***
 * @author 睡醒继续做梦
 * @date 2023/5/11 14:00
 */
@Data
@AllArgsConstructor
public class LimitOfUser {

    private Boolean register;
    private Boolean chat;

}
