package com.example.api.common.rep.auth.jpa.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    @Query("select m from UserEntity m join fetch m.authRole a where m.email = :email")
    Optional<UserEntity> findByEmailWithAuth(@Param("email")String email);


}