package com.example.api.common.config;

import com.example.api.common.config.jwt.provider.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

@RequiredArgsConstructor
@Configuration
public class AuditConfig implements AuditorAware<String> {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public Optional<String> getCurrentAuditor() {
        try {
            String currentUserEmail = jwtTokenProvider.getUserEmail();
            return Optional.of(currentUserEmail);
        } catch (ClassCastException e) {
            return Optional.empty();
        }
    }
}
