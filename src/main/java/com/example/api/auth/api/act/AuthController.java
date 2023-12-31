package com.example.api.auth.api.act;

import com.example.api.auth.api.biz.AuthService;
import com.example.api.auth.rep.jpa.user.UserEntity;
import com.example.api.common.base.dto.DataResponseDTO;
import com.example.api.common.base.dto.ResponseDTO;
import com.example.api.common.base.vo.Code;
import com.example.api.auth.rep.jpa.user.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = {"/api/auth"})
public class AuthController {

    private final AuthService authService;

//    @GetMapping("/ctf/user/{userEmail}")
//    public UserVO getUserInfo(@PathVariable(name = "userEmail") String userEmail) {
//        return userService.getUserInfo(userEmail);
//    }

    @GetMapping("/check")
    public ResponseDTO getCheck() {
        return ResponseDTO.of(true, Code.OK);
    }

    @GetMapping("/userInfo")
    public DataResponseDTO getUserInfo(UserEntity userEntity) {
        UserDTO userDTO = new UserDTO(userEntity);
        return DataResponseDTO.of(userDTO);
    }

    @GetMapping("/getUserEmail")
    public String getUserEmail(Authentication authentication) {
        return authentication.getName();
    }

}
