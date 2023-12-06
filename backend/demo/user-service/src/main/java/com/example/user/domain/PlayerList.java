package com.example.user.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/***
 * @author 睡醒继续做梦
 * @date 2023/3/16 15:30
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("play_list")
public class PlayerList {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String player1;
    private String player2;
    private String loser;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date date;
}
