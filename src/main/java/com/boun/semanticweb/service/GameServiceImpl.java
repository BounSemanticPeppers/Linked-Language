package com.boun.semanticweb.service;

import com.boun.semanticweb.base.CommonUserOperations;
import com.boun.semanticweb.model.*;
import com.boun.semanticweb.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameUsersRepository gameUsersRepository;

    @Autowired
    private GameUserWordsRepository gameUserWordsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WordRelationRepository wordRelationRepository;

    @Autowired
    private WordRelationHistoryRepository wordRelationHistoryRepository;

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

    @Override
    public Game getIncompleteGame() {

        List<Game> games = gameRepository.getIncompleteGames(CommonUserOperations.getUserId());

        if(games.size() > 0){
            return games.get(0);
        }else{
            return null;
        }
    }

    public Game controlAndFinishGame(){
        Long gameId = CommonUserOperations.getCurrentGame().getGameId();
        Game game = gameRepository.getByGameId(gameId);
        List<GameUsers> gameUsersList = gameUsersRepository.findByGameId(gameId);
        if (gameUsersList.size() == 2 && gameUsersList.get(0).getFinished() && gameUsersList.get(1).getFinished()){

            List<GameUserWords> gameUserWords1 = gameUserWordsRepository.findByGameUserId(gameUsersList.get(0).getGameUserId());
            List<GameUserWords> gameUserWords2 = gameUserWordsRepository.findByGameUserId(gameUsersList.get(1).getGameUserId());



            int totalScore = 0;
            for (int i = 0; i<gameUserWords1.size(); i++){
                for (int j=0; j<gameUserWords2.size(); j++){
                    if(gameUserWords1.get(i).getWordId() == gameUserWords2.get(j).getWordId()){
                        totalScore += 10;

                        // add a new word relation to history table
                        WordRelationHistory wordRelationHistory = new WordRelationHistory();
                        wordRelationHistory.setCreatedDate(new Date());
                        wordRelationHistory.setGameId(gameId);
                        wordRelationHistory.setSourceWordId(game.getAskedWordId());
                        wordRelationHistory.setTargetWordId(gameUserWords1.get(i).getWordId());
                        wordRelationHistoryRepository.save(wordRelationHistory);


                        // increment relation counter of the words and save
                        WordRelation wordRelation = wordRelationRepository.findBySourceWordIdAndTargetWordId(game.getAskedWordId(), gameUserWords1.get(i).getWordId());
                        if(wordRelation != null){
                            wordRelation.setScore(wordRelation.getScore() + 1);
                        }else{
                            wordRelation = new WordRelation();
                            wordRelation.setScore(1);
                            wordRelation.setSourceWordId(game.getAskedWordId());
                            wordRelation.setTargetWordId(gameUserWords1.get(i).getWordId());
                        }
                        wordRelationRepository.save(wordRelation);

                    }
                }
            }


            game.setComplete(true);
            game.setScore(totalScore);
            gameRepository.save(game);

            User user1 = userRepository.findById(gameUsersList.get(0).getUserId());
            user1.setTotalScore(user1.getTotalScore() + totalScore);
            userRepository.save(user1);

            User user2 = userRepository.findById(gameUsersList.get(1).getUserId());
            user2.setTotalScore(user2.getTotalScore() + totalScore);
            userRepository.save(user2);
        }

        return game;
    }
}
