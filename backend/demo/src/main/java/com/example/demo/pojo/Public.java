package com.example.demo.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 睡醒继续做梦
 * @date 2022/12/19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("public")
public class Public {
    private int id;
    private String git ;
    private String backlist;
}
