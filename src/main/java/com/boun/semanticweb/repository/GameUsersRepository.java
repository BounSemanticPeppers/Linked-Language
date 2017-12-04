package com.boun.semanticweb.repository;

import com.boun.semanticweb.model.GameUsers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameUsersRepository extends JpaRepository<GameUsers, Long> {
    GameUsers findByGameUserId(Long gameUserId);
}
