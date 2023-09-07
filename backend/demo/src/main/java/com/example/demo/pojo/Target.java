package com.example.demo.pojo;

import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @TableName target
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value ="target", autoResultMap = true)
public class Target implements Serializable {

    @Id
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String target;

    private String content;

    private String icon;

    private String color;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField("createTime")
    private Date createTime;

    @TableField(value = "images", typeHandler = JacksonTypeHandler.class)
    private List<String> images;


    @TableField(value = "tabs", typeHandler = JacksonTypeHandler.class)
    private List<Tab> tabs;


    @TableField(value = "address", typeHandler = JacksonTypeHandler.class)
    private List<Address> address;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Address {
        private String address;
        private String name;
        private String type;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Tab {
        private String name;
        private String tabs;
    }
}