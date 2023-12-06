package com.example.user.service.impl;


import com.example.user.domain.PlayerList;
import com.example.user.mapper.PlayListMapper;
import com.example.user.service.PlayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/***
 * @author 睡醒继续做梦
 * @date 2023/3/16 15:33
 */
@Service
public class PlayListImpl implements PlayList {

    @Autowired
    PlayListMapper playListMapper;

    @Override
    public void add(String a, String b, String loser) {
        playListMapper.insert(new PlayerList(0,a,b,loser,new Date()));
    }
}
