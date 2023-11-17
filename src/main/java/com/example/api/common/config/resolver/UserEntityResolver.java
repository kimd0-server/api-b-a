package com.example.api.common.config.resolver;

import com.example.api.common.base.vo.Code;
import com.example.api.common.config.jwt.provider.JwtTokenProvider;
import com.example.api.common.exception.GeneralException;
import com.example.api.auth.rep.jpa.user.UserEntity;
import com.example.api.auth.rep.jpa.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserEntityResolver implements HandlerMethodArgumentResolver {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(UserEntity.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        UserEntity userEntity = new UserEntity();
        String userEmail = jwtTokenProvider.getUserEmail();
        if (userEmail != null) {
            userEntity = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new GeneralException(Code.NO_SEARCH_USER, "회원이 없습니다."));
//        if (!userEntity.getUserEmail().equals(jwtTokenProvider.getCurrentUserEmail())) {
            if (!userEntity.getEmail().equals(jwtTokenProvider.getUserEmail())) {
                throw new GeneralException(Code.VALIDATION_ERROR, "회원 정보가 일치하지 않습니다.");
            }
        }
        return userEntity;
    }
}
