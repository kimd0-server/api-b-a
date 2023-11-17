package com.example.api.auth.rep.jpa.auth;

import com.example.api.auth.rep.jpa.user.UserEntity;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "PUB_AUTH_TB")
public class AuthEntity implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "role", nullable = false, length = 30)
    private String role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

    public static AuthEntity ofUser(UserEntity userEntity) {
        return AuthEntity.builder()
                .role("ROLE_USER")
                .userEntity(userEntity)
                .build();
    }

    public static AuthEntity ofAdmin(UserEntity userEntity) {
        return AuthEntity.builder()
                .role("ROLE_ADMIN")
                .userEntity(userEntity)
                .build();
    }

    @Override
    public String getAuthority() {
        return role;
    }
}
