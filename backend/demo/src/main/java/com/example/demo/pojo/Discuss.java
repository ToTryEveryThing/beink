package com.example.demo.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName discuss
 */
@TableName(value ="discuss")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Discuss implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 评论的内容
     */
    private String content;


    /**
     * 作者文章的id
     */
    private Integer articleId;

    /**
     * 发表评论的用户名
     */
    private String userName;



    private int up;

    /**
     * 评论时间
     */
    private Date date;

    @Version
    @TableField("version")
    private int  Version;

    public Discuss(String content, Integer articleId, String userName, Date date) {
        this.content = content;
        this.userName =userName;
        this.articleId = articleId;
        this.date = date;
    }
}