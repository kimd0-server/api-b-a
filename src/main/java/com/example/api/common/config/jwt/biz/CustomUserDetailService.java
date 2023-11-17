package com.example.api.common.config.jwt.biz;

import com.example.api.common.base.vo.Code;
import com.example.api.common.config.jwt.cache.CacheKey;
import com.example.api.common.config.jwt.vo.CustomUserDetailVO;
import com.example.api.common.exception.GeneralException;
import com.example.api.auth.rep.jpa.user.UserEntity;
import com.example.api.auth.rep.jpa.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Cacheable(value = CacheKey.USER, key = "#email", unless = "#result == null")
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findByEmailWithRole(email)
                .orElseThrow(() -> new GeneralException(Code.NO_SEARCH_USER, "없는 회원입니다."));
        return CustomUserDetailVO.of(userEntity);
    }
}