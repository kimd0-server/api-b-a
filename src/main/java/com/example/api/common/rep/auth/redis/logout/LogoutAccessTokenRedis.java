package com.example.api.common.rep.auth.redis.logout;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import javax.persistence.Id;

@Getter
@RedisHash("logoutAccessToken")
@AllArgsConstructor
@Builder
public class LogoutAccessTokenRedis {
    @Id
    private String id;

    private String userEmail;

    @TimeToLive
    private Long expiration;

    public static LogoutAccessTokenRedis of(String accessToken, String userEmail, Long remainingMilliSeconds) {
        return LogoutAccessTokenRedis.builder()
                .id(accessToken)
                .userEmail(userEmail)
                .expiration(remainingMilliSeconds / 1000)
                .build();
    }
}
