package com.example.api.auth.api.biz;

import com.example.api.auth.api.dto.TokenDTO;
import com.example.api.auth.rep.redis.refresh.RefreshTokenRedis;
import com.example.api.common.base.vo.Code;
import com.example.api.common.config.jwt.enums.JwtExpirationEnums;
import com.example.api.common.config.jwt.enums.JwtHeaderUtilEnums;
import com.example.api.common.config.jwt.provider.JwtTokenProvider;
import com.example.api.common.exception.GeneralException;
import com.example.api.common.utils.CookieUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RefreshService {
    private final JwtTokenProvider jwtTokenProvider;
    @Transactional(readOnly = true)
    public TokenDTO refresh(String refreshToken) {
        refreshToken = jwtTokenProvider.resolveToken(refreshToken);
        String userEmail = jwtTokenProvider.getUserEmail(refreshToken);
        RefreshTokenRedis redisRefreshToken = jwtTokenProvider.findById(userEmail)
                .orElseThrow(() -> new GeneralException(Code.NO_SEARCH_USER));
        if (!refreshToken.equals(redisRefreshToken.getRefreshToken())) {
            CookieUtils.deleteCookie("RefreshToken");
            throw new GeneralException(Code.TOKEN_ILLEGAL_ARGUMENT);
        }

        TokenDTO tokenDTO = refreshRefreshToken(refreshToken, userEmail);
        CookieUtils.createCookie("RefreshToken", JwtHeaderUtilEnums.GRANT_TYPE.getValue() + tokenDTO.getRefreshToken());

        return tokenDTO;
    }

    private TokenDTO refreshRefreshToken(String refreshToken, String userEmail) {
        if (lessThanRefreshExpirationTimesLeft(refreshToken)) {
            return TokenDTO.of(jwtTokenProvider.generateAccessToken(userEmail), jwtTokenProvider.saveRefreshToken(userEmail).getRefreshToken());
        }
        return TokenDTO.of(jwtTokenProvider.generateAccessToken(userEmail), refreshToken);
    }

    private boolean lessThanRefreshExpirationTimesLeft(String refreshToken) {
        return jwtTokenProvider.getRemainMilliSeconds(refreshToken) < JwtExpirationEnums.REFRESH_EXPIRATION_TIME.getValue();
    }
}
