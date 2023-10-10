package com.example.api.auth.api.biz;

import com.example.api.common.config.jwt.provider.JwtTokenProvider;
import com.example.api.common.rep.auth.jpa.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

//    public UserDTO getUserInfo(String userEmail) {
//        UserEntity userEntity = userRepository.findByUserEmail(userEmail)
//                .orElseThrow(() -> new NoSuchElementException("회원이 없습니다."));
//        if (!userEntity.getUserEmail().equals(jwtTokenProvider.getCurrentUserEmail())) {
//            throw new IllegalArgumentException("회원 정보가 일치하지 않습니다.");
//        }
//        return UserDTO.builder()
//                .userEmail(userEntity.getUserEmail())
//                .userNick(userEntity.getUserNick())
//                .userAuthRole(userEntity.getRoles())
//                .build();
//    }

}
