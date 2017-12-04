package com.boun.semanticweb.service;

import com.boun.semanticweb.model.Game;
import com.boun.semanticweb.model.GameUserWords;
import com.boun.semanticweb.model.GameUsers;

public interface GameService {
    Game startGame(Long wordId);

    GameUsers addUserToGame(Long userId, Long gameId);

    GameUserWords addWordsToGameUser(Long wordId);

    GameUsers finishGameForUser(Long gameUserId);
}
