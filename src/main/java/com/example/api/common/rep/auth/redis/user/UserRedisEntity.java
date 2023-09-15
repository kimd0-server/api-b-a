package com.example.api.common.rep.auth.redis.user;

import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@RedisHash(value = "userRedis", timeToLive = 30)
public class UserRedisEntity {

    @Id
    private String id;
    private String name;
    private Integer age;
    private LocalDateTime createdAt;

}