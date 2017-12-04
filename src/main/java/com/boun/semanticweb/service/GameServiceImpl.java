package com.boun.semanticweb.service;

import com.boun.semanticweb.base.CommonUserOperations;
import com.boun.semanticweb.model.Game;
import com.boun.semanticweb.model.GameUserWords;
import com.boun.semanticweb.model.GameUsers;
import com.boun.semanticweb.repository.GameRepository;
import com.boun.semanticweb.repository.GameUserWordsRepository;
import com.boun.semanticweb.repository.GameUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameUsersRepository gameUsersRepository;

    @Autowired
    private GameUserWordsRepository gameUserWordsRepository;

    @Override
    public Game startGame(Long wordId) {
        Game newGame = new Game();
        newGame.setAskedWordId(wordId);
        newGame.setComplete(false);
        newGame.setScore(0);
        newGame.setCreatedBy(CommonUserOperations.getUserId());
        newGame.setCreatedDate(new Date());
        return gameRepository.save(newGame);


    }

    @Override
    public GameUsers addUserToGame(Long userId, Long gameId) {
        GameUsers newGameUser = new GameUsers();
        newGameUser.setGameId(gameId);
        newGameUser.setUserId(userId);
        newGameUser.setCreatedDate(new Date());
        newGameUser.setFinished(false);

        return gameUsersRepository.save(newGameUser);
    }

    @Override
    public GameUserWords addWordsToGameUser(Long wordId) {
        GameUserWords gameUserWords = new GameUserWords();
        gameUserWords.setGameUserId(CommonUserOperations.getGameUserId());
        gameUserWords.setWordId(wordId);
        gameUserWords.setCreatedDate(new Date());

        return gameUserWordsRepository.save(gameUserWords);
    }

    @Override
    public GameUsers finishGameForUser(Long gameUserId) {
        GameUsers gameUsers = gameUsersRepository.findByGameUserId(gameUserId);
        gameUsers.setFinished(true);
        return gameUsersRepository.save(gameUsers);
    }
}
