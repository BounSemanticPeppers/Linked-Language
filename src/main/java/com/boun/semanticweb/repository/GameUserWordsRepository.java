package com.boun.semanticweb.repository;

import com.boun.semanticweb.model.GameUserWords;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameUserWordsRepository extends JpaRepository<GameUserWords , Long> {
}
