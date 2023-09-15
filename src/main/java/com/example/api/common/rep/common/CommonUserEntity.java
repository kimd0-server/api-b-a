package com.example.api.common.rep.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
public abstract class CommonUserEntity extends CommonDateEntity{
    @CreatedBy
    @Column(name = "create_user", updatable = false, length = 300)
    private String createUser;

    @LastModifiedBy
    @Column(name = "update_user", length = 300)
    private String updateUser;
}
