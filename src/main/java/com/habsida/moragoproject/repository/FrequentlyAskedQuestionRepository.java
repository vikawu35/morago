package com.habsida.moragoproject.repository;

import com.habsida.moragoproject.model.entity.FrequentlyAskedQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FrequentlyAskedQuestionRepository extends JpaRepository<FrequentlyAskedQuestion, Long> {
}
