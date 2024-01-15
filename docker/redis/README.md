```
docker run --name redis \ 
-p 6379:6379 \
-v /home/test/redis/redis.conf:/etc/redis/redis.conf \ 
-v /home/test/redis:/data  \
-d redis \
redis-server /etc/redis/redis.conf \
--appendonly yes
```

