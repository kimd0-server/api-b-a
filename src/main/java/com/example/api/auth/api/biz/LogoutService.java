//package com.example.api.auth.api.biz;
//
//import com.example.auth.api.auth.dto.TokenDTO;
//import com.example.auth.config.jwt.cache.CacheKey;
//import com.example.auth.config.jwt.provider.JwtTokenProvider;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.stereotype.Service;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class LogoutService {
//    private final JwtTokenProvider jwtTokenProvider;
//
//    // 4
//    @CacheEvict(value = CacheKey.USER, key = "#userEmail")
//    public void logout(TokenDTO tokenDTO, String userEmail) {
//        String accessToken = jwtTokenProvider.resolveToken(tokenDTO.getAccessToken());
//        String refreshToken = jwtTokenProvider.resolveToken(tokenDTO.getRefreshToken());
//        long remainMilliSeconds = jwtTokenProvider.getRemainMilliSeconds(refreshToken);
//        jwtTokenProvider.deleteByIdRefreshRedis(userEmail);
//        jwtTokenProvider.saveLogoutAccessRedis(accessToken, userEmail, remainMilliSeconds);
//    }
//
//}
