package com.example.api.auth.api.act;

import com.example.api.auth.api.biz.RefreshService;
import com.example.api.auth.api.dto.TokenDTO;
import com.example.api.common.base.dto.DataResponseDTO;
import com.example.api.common.base.vo.Code;
import com.example.api.common.config.jwt.enums.JwtHeaderUtilEnums;
import com.example.api.common.exception.GeneralException;
import com.example.api.common.utils.CookieUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = {"/api/auth"})
public class RefreshController {
    private final RefreshService refreshService;
    @PostMapping("/refresh")
    public DataResponseDTO<TokenDTO> refresh(@CookieValue(name = "RefreshToken", required = false) String refreshToken) {
        if (refreshToken == null) {
            throw new GeneralException(Code.TOKEN_ILLEGAL_ARGUMENT);
        }
        TokenDTO tokenDTO = refreshService.refresh(refreshToken);
        CookieUtils.createCookie("RefreshToken", JwtHeaderUtilEnums.GRANT_TYPE.getValue() + tokenDTO.getRefreshToken());
        return DataResponseDTO.of(tokenDTO);
    }
}
