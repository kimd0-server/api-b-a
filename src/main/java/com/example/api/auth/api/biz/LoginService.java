package com.example.api.auth.api.biz;

import com.example.api.auth.api.dto.LoginParamDTO;
import com.example.api.auth.api.dto.TokenDTO;
import com.example.api.common.base.vo.Code;
import com.example.api.common.config.jwt.enums.JwtHeaderUtilEnums;
import com.example.api.common.config.jwt.provider.JwtTokenProvider;
import com.example.api.common.exception.GeneralException;
import com.example.api.common.rep.auth.jpa.user.UserEntity;
import com.example.api.common.rep.auth.jpa.user.UserRepository;
import com.example.api.common.rep.auth.redis.refresh.RefreshTokenRedis;
import com.example.api.common.utils.CookieUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public TokenDTO login(LoginParamDTO loginParamDTO) {
        UserEntity userEntity = userRepository.findByEmail(loginParamDTO.getUserEmail())
                .orElseThrow(() -> new GeneralException(Code.NO_SEARCH_USER, "회원이 없습니다."));
        checkPassword(loginParamDTO.getUserPassword(), userEntity.getPassword());

        String userEmail = userEntity.getEmail();
        String accessToken = jwtTokenProvider.generateAccessToken(userEmail);
        RefreshTokenRedis refreshTokenRedis = jwtTokenProvider.saveRefreshToken(userEmail);

        CookieUtils.createCookie("RefreshToken", JwtHeaderUtilEnums.GRANT_TYPE.getValue() + refreshTokenRedis.getRefreshToken());

        return TokenDTO.of(accessToken, refreshTokenRedis.getRefreshToken());
    }

    private void checkPassword(String rawPassword, String findMemberPassword) {
        if (!passwordEncoder.matches(rawPassword, findMemberPassword)) {
            throw new GeneralException(Code.NOT_MATCH_PASSWORD, "비밀번호가 맞지 않습니다.");
        }
    }
}
