package com.example.api.auth.api.biz;

import com.example.api.auth.api.dto.JoinParamDTO;
import com.example.api.auth.rep.jpa.user.UserEntity;
import com.example.api.auth.rep.jpa.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;


@Slf4j
@RequiredArgsConstructor
@Service
public class JoinService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Transactional
    public void joinUser(JoinParamDTO joinParamDTO) {
        joinParamDTO.setUserPassword(passwordEncoder.encode(joinParamDTO.getUserPassword()));
        userRepository.save(UserEntity.ofUser(joinParamDTO));
    }

    @Transactional
    public void joinAdmin(JoinParamDTO joinParamDTO) {
        joinParamDTO.setUserPassword(passwordEncoder.encode(joinParamDTO.getUserPassword()));
        userRepository.save(UserEntity.ofAdmin(joinParamDTO));
    }

    @Transactional
    public void joinSocial(JoinParamDTO joinParamDTO) {
        joinParamDTO.setUserPassword(passwordEncoder.encode(joinParamDTO.getUserPassword()));
        userRepository.save(UserEntity.ofSocial(joinParamDTO));
    }

    public Boolean valid(JoinParamDTO joinParamDTO, BindingResult bindingResult) {
        // loginId 중복 체크
        if(this.duplEmail(joinParamDTO.getUserEmail())) {
            bindingResult.addError(new FieldError("joinParamDTO", "userEmail", "로그인 아이디가 중복됩니다."));
        }
        // 닉네임 중복 체크
        if(this.duplNick(joinParamDTO.getUserNick())) {
            bindingResult.addError(new FieldError("joinParamDTO", "userNick", "닉네임이 중복됩니다."));
        }
        // password와 passwordCheck가 같은지 체크
        if(!joinParamDTO.getUserPassword().equals(joinParamDTO.getUserPasswordCheck())) {
            bindingResult.addError(new FieldError("joinParamDTO", "userPasswordCheck", "바밀번호가 일치하지 않습니다."));
        }
        return bindingResult.hasErrors();
    }

//    @Transactional
//    public UserEntity create(JoinParamVO userJoinFormVO) {
//        UserEntity userVO = null;
//
//        if (isDuplicatedEmail(userJoinFormVO.getUserEmail())) {
//            return null;
//        }
//
//        if (isDuplicatedNickName(userJoinFormVO.getUserNick())) {
//            return null;
//        }
//
//        try {
//            userVO = UserEntity.builder()
//                .userEmail(userJoinFormVO.getUserEmail())
//                .userPassword(passwordEncoder.encode(userJoinFormVO.getUserPassword()))
//                .userName(userJoinFormVO.getUserName())
//                .userPhone(userJoinFormVO.getUserPhone())
//                .userNick(userJoinFormVO.getUserNick())
//                .userIp(Inet4Address.getLocalHost().getHostAddress())
//                .userBlock("N")
//                .build();
//        } catch (UnknownHostException e) {
//            throw new RuntimeException(e);
//        }
//
//        return userRepository.save(userVO);
//    }

    public Boolean duplEmail(String email) {
        return isDuplicatedEmail(email);
    }

    public Boolean duplNick(String nick) {
        return isDuplicatedNickName(nick);
    }

    @Transactional(readOnly = true)
    public boolean isDuplicatedEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Transactional(readOnly = true)
    public boolean isDuplicatedNickName(String nick) {
        return userRepository.existsByNick(nick);
    }
}
