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
 * @TableName follow
 */
@TableName(value ="follow")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Follow implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 关注者（粉丝）
     */
    private Integer followerId;

    /**
     * 被关注者（用户）
     */
    private Integer followingId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}