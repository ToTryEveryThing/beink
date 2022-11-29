package com.example.demo.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
//表格名字
@TableName("user")
public class user {
    @TableId//主键
    private int id;
    private String account;
    @JsonIgnore//忽略 不展示给前端
    private String password;
}
