package com.boun.semanticweb.repository;

import com.boun.semanticweb.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GameRepository  extends JpaRepository<Game,Long> {
    Game getByGameId(Long gameId);

    @Query("SELECT g FROM Game g WHERE g.gameId not in ( select ga.gameId from GameUsers ga where ga.userId = ?1) and g.complete = 0")
    List<Game> getIncompleteGames(Long userId);
}
