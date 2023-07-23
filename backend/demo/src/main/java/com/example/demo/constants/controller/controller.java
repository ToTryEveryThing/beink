package com.example.demo.constants.controller;

/***
 * @author 睡醒继续做梦
 * @date 2023/4/12 7:58
 */
public class controller {

    /**
     * 不需要授权访问
     */
    public static final String[] patterns = {
            "/user/admin/git/show/","/user/account/token/",
            "/user/account/register/","/user/admin/backlist/show/",
            "/oss/getList/","/captcha/",
            "/user/discuss/show/","/translate/query/","/user/article/showbyid/",
            "/user/article/showall/","/user/article/showone/",
            "/follow/getfollowerlist/","/follow/getfollowinglist/",
            "/search/article/{content}/"
    };

    /**
     * 获取用户信息
     */
    public static final String[] USER_INFO  = {
            "/user/account/color/",
            "/user/admin/git/save/","/user/discuss/add/",
            "/user/discuss/delete/","/user/up/","/user/down/","/user/discussstatus/",
            "/user/mailbind/","/user/chat/content/","/user/article/delete/","/user/article/edit/",
            "/user/article/add/","/user/article/up/","/user/account/changpasswd/","/user/upload/image/"
    };

    /**
     * 验证码
     */
    public static final String[] CAPTCHA = {
//            "/user/account/token/","/user/account/register/"
    };

}
