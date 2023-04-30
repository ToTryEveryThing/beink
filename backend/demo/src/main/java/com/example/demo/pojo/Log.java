package com.example.demo.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/***
 * @author 睡醒继续做梦
 * @date 2023/4/30 9:40
 */

@Data
@TableName(value = "op_log")
public class Log {

    /**
     * 权限角色ID
     */
    @TableId(type = IdType.AUTO)
    private int id;

    @TableField(value = "username")
    private String username; //用户名

    @TableField(value = "`method`")
    private String method; //方法名

    @TableField(value = "params")
    private String params; //参数

    @TableField(value = "`ip`")
    private String ip; //ip地址

    @TableField(value = "url")
    private String url; //请求url

    @TableField(value = "`type`")
    private String type; //操作类型 :新增、删除等等

//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @TableField(value = "create_date")
    private Date createDate; //操作时间

    @TableField(value = "`result`")
    private String result; //操作结果

    @TableField(value = "description")
    private String description;//描述

}
