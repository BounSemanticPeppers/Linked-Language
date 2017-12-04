package com.boun.semanticweb.repository;

import com.boun.semanticweb.model.GameUserWords;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameUserWordsRepository extends JpaRepository<GameUserWords , Long> {

    List<GameUserWords> findByGameUserId(Long gameUserId);
}
