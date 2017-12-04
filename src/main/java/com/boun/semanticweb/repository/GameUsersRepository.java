package com.boun.semanticweb.repository;

import com.boun.semanticweb.model.GameUsers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameUsersRepository extends JpaRepository<GameUsers, Long> {
    GameUsers findByGameUserId(Long gameUserId);

    List<GameUsers> findByGameId(Long gameId);
}
