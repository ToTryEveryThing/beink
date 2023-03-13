package com.example.demo.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

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
    @TableField("date")
    private Date date;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}