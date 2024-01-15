```sh
docker run -d \
  --name mysql \
  -p 3306:3306 \
  -e TZ=Asia/Shanghai \
  -e MYSQL_ROOT_PASSWORD=123456 \
  -v /home/test/mysql/data:/var/lib/mysql \
  -v /home/test/mysql/conf:/etc/mysql/conf.d \
  -v /home/test/mysql/init:/docker-entrypoint-initdb.d \
  mysql
```

