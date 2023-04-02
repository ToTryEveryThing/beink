package com.example.demo.pojo.user;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 睡醒继续做梦
 * @date 2022/12/5
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("web")
public class Record {
    private int id;
    private String account;
    private Date date;
    private String backimg;
    private String mail;
    private String role;
}
