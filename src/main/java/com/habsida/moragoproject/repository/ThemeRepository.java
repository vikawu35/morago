package com.habsida.moragoproject.repository;

import com.habsida.moragoproject.model.entity.Theme;
import com.habsida.moragoproject.model.enums.ETheme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ThemeRepository extends JpaRepository<Theme, Long> {
    Optional<Theme> findByName (ETheme eTheme);
}
