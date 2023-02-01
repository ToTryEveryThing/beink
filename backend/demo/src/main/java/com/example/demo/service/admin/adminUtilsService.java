package com.example.demo.service.admin;

/**
 * @author 睡醒继续做梦
 * @date 2022/11/26
 */
public interface adminUtilsService {
    String save(String markdown);
    String show();
    String save_list(String backlist);
    String show_list();

    String redisShow();
}
