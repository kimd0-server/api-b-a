package com.example.api.common.config.jwt.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JwtExpirationEnums {
    ACCESS_TOKEN_EXPIRATION_TIME("JWT 만료 시간 / 60초", 1000L * 60),
//    ACCESS_TOKEN_EXPIRATION_TIME("JWT 만료 시간 / 30분", 1000L * 60 * 30),
    REFRESH_TOKEN_EXPIRATION_TIME("Refresh 토큰 만료 시간 / 7일", 1000L * 60 * 60 * 24 * 3),
    REFRESH_EXPIRATION_TIME("Refresh 토큰 재발급 시간 / 3일", 1000L * 60 * 60 * 24 * 3);

    private String description;
    private Long value;
}
