package com.habsida.moragoproject.repository;

import com.habsida.moragoproject.model.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    Optional<File> findByOriginalTitle(String originalTitle);
}
