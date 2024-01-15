```
docker run -d --name nginx \
-p 8080:8080 \ 
-v /home/test/nginx/html:/etc/nginx/html \
-v /home/test/nginx/nginx.conf:/etc/nginx/nginx.conf \ 
--network beink \ 
nginx
```

