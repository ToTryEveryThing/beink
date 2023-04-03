package com.example.demo.pojo.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class web {
    @TableId(type = IdType.AUTO)
    private int id;
    private String account;
    private String password;
    private String backimg;
    private String list;
    private Date date;
    private String role;

    private String mail;
}