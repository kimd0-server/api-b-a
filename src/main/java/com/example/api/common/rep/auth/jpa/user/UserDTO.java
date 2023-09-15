package com.example.api.common.rep.auth.jpa.user;

import lombok.Data;

@Data
public class UserDTO {
    private Long userId;
    private String userOAuth2Id;
    private String userEmail;
    private String userName;
    private String userPhone;
    private String userNick;
    private String userIp;
    private String userBlock;
    private Long userWalletEntityPoint;

    public UserDTO(UserEntity userEntity) {
        this.userId = userEntity.getId();
        this.userOAuth2Id = userEntity.getOAuth2Id();
        this.userEmail = userEntity.getEmail();
        this.userName = userEntity.getName();
        this.userPhone = userEntity.getPhone();
        this.userNick = userEntity.getNick();
        this.userIp = userEntity.getIp();
        this.userBlock = userEntity.getBlock();
        this.userWalletEntityPoint = userEntity.getWalletEntity().getWalletPoint();
    }
}
