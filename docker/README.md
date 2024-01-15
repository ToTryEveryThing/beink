- docker pull redis
- docker pull mysql
- docker pull nginx

![image-20240115160706855](assets/image-20240115160706855.png)

![image-20240115160732073](assets/image-20240115160732073.png)

![image-20240115160747316](assets/image-20240115160747316.png)

```
 mv demo-0.0.1-SNAPSHOT.jar beink.jar
```

```
docker build -t beink .
```

```
docker run -d --name beink --network beink  -p 9090:9090 beink
```

