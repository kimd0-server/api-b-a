package com.example.api.common.rep.auth.jpa.user;

import com.example.api.common.rep.auth.jpa.auth.AuthEntity;
import com.example.api.common.rep.common.CommonDateEntity;
import com.example.api.common.rep.auth.jpa.wallet.WalletEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Entity
@Table(name = "CAUTH_USER_TB")
public class UserEntity extends CommonDateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "oauth2_id", unique = true)
    @ColumnDefault("''")
    private String oAuth2Id;

    @Column(name = "email", nullable = false, length = 45, unique = true)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password", nullable = false, length = 300)
    private String password;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(name = "phone", nullable = false, length = 30)
    private String phone;

    @Column(name = "nick", nullable = false, length = 30)
    private String nick;

    @Column(name = "ip", nullable = false, length = 60)
    private String ip;

    @ColumnDefault("'n'")
    @Column(name = "block", columnDefinition = "char", length = 1)
    private String block;

    @OneToOne(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private WalletEntity walletEntity;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<AuthEntity> authRole = new HashSet<>();

    public List<String> getRoles() {
        return authRole.stream()
                .map(AuthEntity::getAuthRole)
                .collect(Collectors.toList());
    }

}
