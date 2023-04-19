package com.example.demo.pojo.article;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/***
 * @author 睡醒继续做梦
 * @date 2023/3/30 17:30
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class article {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String post;
    private String title;
    private String content;
    private Integer up;
    private Integer views;

    private Integer discuss;
    private Date time;

    private Boolean isshow;

    @Version
    private Integer  version;
}
