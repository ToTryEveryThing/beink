package com.example.demo.pojo.article;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/***
 * @author 睡醒继续做梦
 * @date 2023/4/2 10:34
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class articleUp {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer articleId;

    private String  userName;

    private Integer status;
}
