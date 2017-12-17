package com.boun.semanticweb.service;

import com.boun.semanticweb.model.Game;
import com.boun.semanticweb.model.GameUserWords;
import com.boun.semanticweb.model.GameUsers;
import com.boun.semanticweb.model.Word;
import com.boun.semanticweb.viewModel.GameRecord;

import java.text.ParseException;
import java.util.List;

public interface GameService {
    Game startGame(Long wordId);

    GameUsers addUserToGame(Long userId, Long gameId);

    GameUserWords addWordsToGameUser(Long wordId);

    GameUsers finishGameForUser(Long gameUserId);

    Game getIncompleteGame();

    Game controlAndFinishGame();

    GameRecord getGame(Long gameId) throws ParseException;

    List<Word> getMatchedWordListOfGame(Long gameId);

    Boolean isGameFinished(Long gameId);

    List<Word> getUnmatchedWordsOfGameUser(Long gameId, Long userId);

    List<Word> getWaitingWordsOfGame(Long gameId);

    List<GameRecord> getUserGames(Long userId) throws ParseException;
}
