package com.example.api.check.act;

import com.example.api.common.rep.auth.jpa.user.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/check")
public class CheckAction {
    @GetMapping("")
    public void check(UserEntity userEntity) {
        log.error("user {}", userEntity);
    }
}
