package com.example.api.auth.api.act;

import com.example.api.auth.api.biz.LoginService;
import com.example.api.auth.api.dto.LoginParamDTO;
import com.example.api.auth.api.dto.TokenDTO;
import com.example.api.common.base.dto.DataResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = {"/api/auth"})
public class LoginController {
    private final LoginService loginService;

    @PostMapping("/login")
    public DataResponseDTO<TokenDTO> login(@RequestBody LoginParamDTO loginParamDTO) {
        return DataResponseDTO.of(loginService.login(loginParamDTO));
    }
}
