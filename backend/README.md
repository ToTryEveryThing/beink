# Spring boot

----
>2022/12/19    study和backlist 分出一个表

### SpringBoot 2.0.x 之后 文件上传大小限制

> ```properties
> spring.servlet.multipart.max-file-size=10MB
> spring.servlet.multipart.max-request-size=10MB
> ```

> 避免拿着Token乱改  限制一下  不过方法不太行😅😅

> 使用aop实现自定义注解 解决权限认证 🎉🎉

### 验证码

[学习地址]([SpringBoot业务开发 02、Springboot快速集成验证码【easy-captcha、kaptcha】超好看样式_springboot集成验证码_长路 ㅤ   的博客-CSDN博客](https://changlu.blog.csdn.net/article/details/124132246?spm=1001.2014.3001.5506))

```xml
<dependency>
    <groupId>com.github.tanshion</groupId>
    <artifactId>easy-captcha</artifactId>
    <version>1.6.2.1</version>
</dependency>
```

