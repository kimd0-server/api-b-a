package com.example.api.auth.api.act;

import com.example.api.auth.api.biz.LogoutService;
import com.example.api.auth.api.dto.TokenDTO;
import com.example.api.common.base.dto.ResponseDTO;
import com.example.api.common.base.vo.Code;
import com.example.api.common.config.jwt.provider.JwtTokenProvider;
import com.example.api.common.utils.CookieUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = {"/api/auth"})
public class LogoutController {
    private final LogoutService logoutService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/logout")
    public ResponseDTO logout(
            @RequestHeader("Authorization") String accessToken,
            @CookieValue("RefreshToken") String refreshToken
    ) {
        CookieUtils.deleteCookie("RefreshToken");
        String userEmail = jwtTokenProvider.getUserEmail(jwtTokenProvider.resolveToken(accessToken));
        logoutService.logout(TokenDTO.of(accessToken, refreshToken), userEmail);

        return ResponseDTO.of(true, Code.OK);
    }
}
