package com.boun.semanticweb.repository;

import com.boun.semanticweb.model.WordRelation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WordRelationRepository extends JpaRepository<WordRelation, Long> {

    WordRelation findById(Long id);

    WordRelation findBySourceWordIdAndTargetWordId(Long sourceWordId, Long targetWordId);

    List<WordRelation> findBySourceWordIdOrTargetWordId(Long sourceWordId, Long targetWordId);
}
