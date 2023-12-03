package com.example.article.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FollowUser {
    private int id;
    private String account;
    private Date date;
    private String role;
    private String mail;
    private String userimage;
}
