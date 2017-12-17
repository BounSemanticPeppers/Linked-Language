package com.boun.semanticweb.service;

import com.boun.semanticweb.base.CommonUserOperations;
import com.boun.semanticweb.model.*;
import com.boun.semanticweb.repository.*;
import com.boun.semanticweb.viewModel.GameRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.text.ParseException;
import java.util.ArrayList;
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

    @Autowired
    private WordRepository wordRepository;

    @Autowired
    private EntityManager entityManager;


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
        if (isGameFinished(gameUsersList)){

            List<Long> matchedWordIds = getMatchedWordIdListOfGame(game.getGameId());
            int totalScore = 0;
            for (int i = 0; i<matchedWordIds.size(); i++){
                totalScore += 10;

                // add a new word relation to history table
                addWordRelationHistory(game, matchedWordIds.get(i));

                // increment relation counter of the words and save
                addWordRelation(game, matchedWordIds.get(i));

            }

            game.setComplete(true);
            game.setScore(totalScore);
            gameRepository.save(game);

            updateUserScore(gameUsersList.get(0).getUserId(),totalScore);
            updateUserScore(gameUsersList.get(1).getUserId(),totalScore);

        }

        return game;
    }

    @Override
    public GameRecord getGame(Long gameId) throws ParseException {
        Query query = entityManager.createNativeQuery("SELECT g.gameId, g.askedWordId, g.complete, " +
                " g.createdDate, g.createdBy, g.score, w.text  FROM game g inner join word w on g.askedWordId = w.id" +
                " WHERE g.gameId = ?1");
        query.setParameter(1, gameId);

        List<Object[]> results = query.getResultList();

        GameRecord gr = new GameRecord(results.get(0));


        return gr;
    }

    public List<Long> getMatchedWordIdListOfGame(Long gameId){
        List<GameUsers> gameUsersList = gameUsersRepository.findByGameId(gameId);
        List<GameUserWords> gameUserWords1 = gameUserWordsRepository.findByGameUserId(gameUsersList.get(0).getGameUserId());
        List<GameUserWords> gameUserWords2 = gameUserWordsRepository.findByGameUserId(gameUsersList.get(1).getGameUserId());

        ArrayList<Long> matchWordIds = new ArrayList<>();
        for (int i = 0; i<gameUserWords1.size(); i++) {
            for (int j = 0; j < gameUserWords2.size(); j++) {
                if (gameUserWords1.get(i).getWordId() == gameUserWords2.get(j).getWordId()) {
                    matchWordIds.add(gameUserWords1.get(i).getWordId());
                }
            }
        }
        return matchWordIds;
    }

    public List<Word> getMatchedWordListOfGame(Long gameId){
        List<Long> matchWordIds = getMatchedWordIdListOfGame(gameId);
        return wordRepository.findByIdIn(matchWordIds);
    }

    @Override
    public Boolean isGameFinished(Long gameId) {
        List<GameUsers> gameUsersList = gameUsersRepository.findByGameId(gameId);
        if (gameUsersList.size() == 2 && gameUsersList.get(0).getFinished() && gameUsersList.get(1).getFinished()){
            return true;
        }
        return false;
    }

    @Override
    public List<Word> getUnmatchedWordsOfGameUser(Long gameId, Long userId) {
        List<GameUsers> gameUsersList = gameUsersRepository.findByGameId(gameId);
        List<GameUserWords> gameUserWords1;
        List<GameUserWords> gameUserWords2;
        if(gameUsersList.get(0).getUserId().equals(userId)){
            gameUserWords1 = gameUserWordsRepository.findByGameUserId(gameUsersList.get(0).getGameUserId());
            gameUserWords2 = gameUserWordsRepository.findByGameUserId(gameUsersList.get(1).getGameUserId());
        }else{
            gameUserWords1 = gameUserWordsRepository.findByGameUserId(gameUsersList.get(1).getGameUserId());
            gameUserWords2 = gameUserWordsRepository.findByGameUserId(gameUsersList.get(0).getGameUserId());
        }

        ArrayList<Long> unmatchWordIds = new ArrayList<>();
        for (int i = 0; i<gameUserWords1.size(); i++) {
            Boolean isMatch = false;
            for (int j = 0; j < gameUserWords2.size(); j++) {
                if (gameUserWords1.get(i).getWordId() == gameUserWords2.get(j).getWordId()) {
                    isMatch = true;
                }
            }
            if(!isMatch){
                unmatchWordIds.add(gameUserWords1.get(i).getWordId());
            }
        }

        return wordRepository.findByIdIn(unmatchWordIds);

    }

    @Override
    public List<Word> getWaitingWordsOfGame(Long gameId) {
        List<GameUsers> users = gameUsersRepository.findByGameId(gameId);
        if(users.size() > 1){
            return new ArrayList<>();
        }
        List<GameUserWords> userWords = gameUserWordsRepository.findByGameUserId(users.get(0).getGameUserId());
        List<Long> wordIds= new ArrayList<>();
        for (int i = 0; i<userWords.size(); i++){
            wordIds.add(userWords.get(i).getWordId());
        }
        return wordRepository.findByIdIn(wordIds);
    }

    @Transactional
    @Override
    public List<GameRecord> getUserGames(Long userId) throws ParseException {

        Query query = entityManager.createNativeQuery("SELECT g.gameId, g.askedWordId, g.complete, " +
                " g.createdDate, g.createdBy, g.score, w.text  FROM game g inner join word w on g.askedWordId = w.id" +
                " WHERE g.gameId IN ( select gu.gameId from gameUsers gu where gu.userId = ?1) ORDER BY g.createdDate desc");
        query.setParameter(1, userId);

        System.out.print(query.toString());
        List<Object[]> results = query.getResultList();

        List<GameRecord> returnList = new ArrayList<>();
        for (Object[] res: results){
            returnList.add(new GameRecord(res));
        }


        return returnList;
    }

    private void updateUserScore(Long userId, Integer totalScore){
        User user = userRepository.findById(userId);
        user.setTotalScore(user.getTotalScore() + totalScore);
        userRepository.save(user);
    }

    private void addWordRelationHistory(Game game, Long feedWordId){

        WordRelationHistory wordRelationHistory = new WordRelationHistory();
        wordRelationHistory.setCreatedDate(new Date());
        wordRelationHistory.setGameId(game.getGameId());
        wordRelationHistory.setSourceWordId(game.getAskedWordId());
        wordRelationHistory.setTargetWordId(feedWordId);
        wordRelationHistoryRepository.save(wordRelationHistory);
    }

    private void addWordRelation(Game game,Long feedWordId){
        WordRelation wordRelation = wordRelationRepository.findBySourceWordIdAndTargetWordId(game.getAskedWordId(), feedWordId);
        if(wordRelation != null){
            wordRelation.setScore(wordRelation.getScore() + 1);
        }else{
            wordRelation = new WordRelation();
            wordRelation.setScore(1);
            wordRelation.setSourceWordId(game.getAskedWordId());
            wordRelation.setTargetWordId(feedWordId);
        }
        wordRelationRepository.save(wordRelation);
    }

    private Boolean isGameFinished(List<GameUsers> gameUsersList){
        if (gameUsersList.size() == 2 && gameUsersList.get(0).getFinished() && gameUsersList.get(1).getFinished()){
            return true;
        }
        return false;
    }
}
