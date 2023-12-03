package com.example.article.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName fans
 */
@TableName(value ="fans")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Fans implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private Integer authorId;

    /**
     * 
     */
    private Integer articleId;

    /**
     * 
     */
    private Integer followerId;

    private String  authorName;

    private  String articleTitle;

    /**
     * 
     */
    @TableField( "`read`")
    private Integer read;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}