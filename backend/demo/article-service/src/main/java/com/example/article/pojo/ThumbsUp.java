package com.example.article.pojo;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@TableName(value = "thumbs_up")
@AllArgsConstructor
@NoArgsConstructor
public class ThumbsUp {
    @TableField(value = "user_name")
    private String userName;
    @TableField(value = "article_id")
    private Integer articleId;
    private Integer count;
    private Boolean status;
}
