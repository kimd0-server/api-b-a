package com.example.api.auth.rep.jpa.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByEmail(String email);
    boolean existsByNick(String nick);
    Optional<UserEntity> findByEmail(String email);
    @Query("select m from UserEntity m join fetch m.role a where m.email = :email")
    Optional<UserEntity> findByEmailWithRole(@Param("email")String email);
}