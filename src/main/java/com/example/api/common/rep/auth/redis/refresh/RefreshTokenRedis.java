package com.example.api.common.rep.auth.redis.refresh;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import javax.persistence.Id;

@Getter
@RedisHash("refreshToken")
@AllArgsConstructor
@Builder
public class RefreshTokenRedis {
    @Id
    private String id;

    private String refreshToken;

    @TimeToLive
    private Long expiration;

    public static RefreshTokenRedis createRefreshToken(String userEmail, String refreshToken, Long remainingMilliSeconds) {
        return RefreshTokenRedis.builder()
                .id(userEmail)
                .refreshToken(refreshToken)
                .expiration(remainingMilliSeconds / 1000)
                .build();
    }
}
