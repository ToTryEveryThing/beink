package com.example.demo.service.impl.utils;

import com.example.demo.controller.common.ApiResponse;
import com.example.demo.utils.redisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***
 * @author 睡醒继续做梦
 * @date 2023/3/1 16:04
 */

@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private redisUtil redisUtil;


    //    抄送（CC），用户给收件人发出邮件的同时把该邮件抄送给另外的人，在这种抄送方式中，“收件人”知道发件人把该邮件抄送给了另外哪些人。
//    发送者 收件人  抄送人 主题 内容
    public ApiResponse sendSimpleMail(String to,
                                      String content){
        if(!validate(to))return ApiResponse.error(0,"false");
        String code = this.code();
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("totryeverything@qq.com");
        msg.setTo(to);
        msg.setSubject("验证码为：" + code);
        msg.setText("如果这不是你所发送的请忽略！");
        redisUtil.set(to,code);
        redisUtil.expire(to,300);
        javaMailSender.send(msg);
        return ApiResponse.success();
    }
//    更新发送邮件 redis_oss
    public void sendRedisOss(String to){
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("totryeverything@qq.com");
        msg.setTo(to);
        msg.setSubject("redis_oss更新了");
        msg.setText("");
        javaMailSender.send(msg);
    }


    //   发送带附件的邮件
    public void sendFailMail(String from,String to,
                             String subject,String content,
                             File file) throws MessagingException {

        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg,true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setText(content);
        helper.setSubject(subject);
        helper.addAttachment(file.getName(),file);
        javaMailSender.send(msg);

    }
    //    附带图片
    public void sendMailImg(String from,String to,
                            String subject,String content,
                            String[] srcPath, String[] resIds) throws MessagingException {

        if(srcPath.length!=resIds.length){
            System.out.println("hello world");
            return ;
        }

        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setText(content,true);//表示正文是HTML格式
        helper.setSubject(subject);
        for(int i=0;i<srcPath.length;i++){
            FileSystemResource res = new FileSystemResource(new File(srcPath[i]));
            helper.addInline(resIds[i],res);
        }
        javaMailSender.send(msg);

    }

    public String code(){
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10));
        }
        String randomString = sb.toString();
//        System.out.println("Random string: " + randomString);
        return randomString;
    }

        private static final Pattern pattern = Pattern.compile(
                "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)"
                        + "|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,}|[0-9]{1,3})(\\]?)$");

        public static boolean validate(String email) {
            Matcher matcher = pattern.matcher(email);
            return matcher.matches();
        }



}