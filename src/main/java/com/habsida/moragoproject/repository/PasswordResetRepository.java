package com.habsida.moragoproject.repository;

import com.habsida.moragoproject.model.entity.PasswordReset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetRepository extends JpaRepository<PasswordReset, Long> {
    Optional<PasswordReset> findByPhone(String phone);
    Boolean existsByToken(String token);
}
