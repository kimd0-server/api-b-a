package com.example.api.common.rep.auth.jpa.wallet;


import com.example.api.common.rep.auth.jpa.user.UserEntity;
import com.example.api.common.rep.common.CommonDateEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PUB_WALLET_TB")
public class WalletEntity extends CommonDateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "point", nullable = false)
    private Long point;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

}