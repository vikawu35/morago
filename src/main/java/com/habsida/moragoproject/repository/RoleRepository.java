package com.habsida.moragoproject.repository;

import com.habsida.moragoproject.model.entity.Role;
import com.habsida.moragoproject.model.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(ERole eRole);
}
