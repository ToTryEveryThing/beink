package com.example.user.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName chat
 */
@TableName(value ="chat")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Chat implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    @TableField( "`from`")
    private String from;

    /**
     * 
     */
    @TableField( "`to`")
    private String to;

    /**
     * 
     */
    @TableField("content")
    private String content;

    /**
     * 
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @TableField("date")
    private Date date;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}