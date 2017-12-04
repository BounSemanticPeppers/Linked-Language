package com.boun.semanticweb.repository;

import com.boun.semanticweb.model.WordRelationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WordRelationHistoryRepository extends JpaRepository<WordRelationHistory,Long> {
    WordRelationHistory findById(Long Id);
}
