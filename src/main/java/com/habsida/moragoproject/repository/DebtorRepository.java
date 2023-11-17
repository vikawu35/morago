package com.habsida.moragoproject.repository;

import com.habsida.moragoproject.model.entity.Debtor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DebtorRepository extends JpaRepository<Debtor, Long> {
}
