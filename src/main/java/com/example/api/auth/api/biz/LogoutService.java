package com.example.api.auth.api.biz;

import com.example.api.auth.api.dto.TokenDTO;
import com.example.api.common.config.jwt.cache.CacheKey;
import com.example.api.common.config.jwt.provider.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LogoutService {
    private final JwtTokenProvider jwtTokenProvider;

    // 4
    @CacheEvict(value = CacheKey.USER, key = "#email")
    public void logout(TokenDTO tokenDTO, String email) {
        String accessToken = jwtTokenProvider.resolveToken(tokenDTO.getAccessToken());
        String refreshToken = jwtTokenProvider.resolveToken(tokenDTO.getRefreshToken());
        long remainMilliSeconds = jwtTokenProvider.getRemainMilliSeconds(refreshToken);
        jwtTokenProvider.deleteByIdRefreshRedis(email);
        jwtTokenProvider.saveLogoutAccessRedis(accessToken, email, remainMilliSeconds);
    }

}
