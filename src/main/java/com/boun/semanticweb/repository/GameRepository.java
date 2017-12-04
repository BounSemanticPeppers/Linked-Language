package com.boun.semanticweb.repository;

import com.boun.semanticweb.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository  extends JpaRepository<Game,Long> {
    Game getByGameId(Long gameId);
}
