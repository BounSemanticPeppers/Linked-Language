/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boun.semanticweb.web;

import com.boun.semanticweb.base.CommonUserOperations;
import com.boun.semanticweb.base.JsonHandler;
import com.boun.semanticweb.model.Game;
import com.boun.semanticweb.model.GameUsers;
import com.boun.semanticweb.model.Word;
import com.boun.semanticweb.service.GameService;
import com.boun.semanticweb.service.WordService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.boun.semanticweb.viewModel.GameRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sun.misc.Request;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author onurm
 */
@Controller
public class GameController {

    @Autowired
    private WordService wordService;

    @Autowired
    private GameService gameService;

    @RequestMapping(value = "/playGame", method = RequestMethod.GET)
    public String playGame(HttpServletResponse response) {
        return "gamePlay";
    }

    @RequestMapping(value = "/gameEnd", method = RequestMethod.GET)
    public String gameEnd(HttpServletResponse response) {
        return "gameEnd";
    }

    @RequestMapping(value = "/getRandomWord", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public void getRandomWord(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Word word = null;
        Game game = null;
        Game incomplete = gameService.getIncompleteGame();

        if (incomplete == null){ // if no incomplete game exists then create new
            word = wordService.getRandomWord();
            game = gameService.startGame(word.getId());
        }else{ //else get existing game
            word = wordService.findByWordId(incomplete.getAskedWordId());
            game = incomplete;
        }

        GameUsers gameUsers = gameService.addUserToGame(CommonUserOperations.getUserId(),game.getGameId());

        HttpSession session = request.getSession();
        session.setAttribute("game", game);
        session.setAttribute("gameUsersId", gameUsers.getGameUserId());
        response.getWriter().write(JsonHandler.convertToJSON(word));

    }

    @RequestMapping(value = "/saveFeedWord", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public void saveFeedWord(@ModelAttribute("feedWord") String feedWord, HttpServletResponse response) throws Exception {

        Word word = wordService.getOrSaveFeedWord(feedWord);
        gameService.addWordsToGameUser(word.getId());

        response.getWriter().write(JsonHandler.convertToJSON(word));

    }

    @RequestMapping(value = "/finishGameForUser", method = RequestMethod.POST)
    public void finishGameForUser(HttpServletResponse response) throws Exception {

        GameUsers gameUsers = gameService.finishGameForUser(CommonUserOperations.getGameUserId());

        Game game = gameService.controlAndFinishGame();
        CommonUserOperations.removeTheGameFromSession();

        response.getWriter().write(JsonHandler.convertToJSON(game));

    }


    @RequestMapping(value = "/getMatchedWordsOfGame" , method = RequestMethod.POST)
    public void getMatchedWordsOfGame(@ModelAttribute("gameId") Long gameId, HttpServletResponse response) throws IOException {
        List<Word> matchedWords = gameService.getMatchedWordListOfGame(gameId);
        response.getWriter().write(JsonHandler.convertToJSON(matchedWords));
    }

    @RequestMapping(value = "/getUnMatchedWordsOfSessionUser" , method = RequestMethod.POST)
    public void getUnMatchedWordsOfSessionUser(@ModelAttribute("gameId") Long gameId, HttpServletResponse response) throws IOException {
        List<Word> unmatchWordsForFirstUser = gameService.getUnmatchedWordsOfGameUser(gameId,CommonUserOperations.getUserId());
        response.getWriter().write(JsonHandler.convertToJSON(unmatchWordsForFirstUser));
    }

    @RequestMapping(value = "/getUnMatchedWordsOfCompetitor" , method = RequestMethod.POST)
    public void getUnMatchedWordsOfCompetitor(@ModelAttribute("gameId") Long gameId, HttpServletResponse response) throws IOException {
        List<Word> unmatchWordsForSecondUser  = gameService.getUnmatchedWordsOfGameUser(gameId,new Long(-1));
        response.getWriter().write(JsonHandler.convertToJSON(unmatchWordsForSecondUser));
    }

    @RequestMapping(value = "/getWaitingWordsOfGame" , method = RequestMethod.POST)
    public void getWaitingWordsOfGame(@ModelAttribute("gameId") Long gameId, HttpServletResponse response) throws IOException {
        List<Word> waitingWords = gameService.getWaitingWordsOfGame(gameId);
        response.getWriter().write(JsonHandler.convertToJSON(waitingWords));
    }

    @RequestMapping(value = "/getGameDetails", method = RequestMethod.POST)
    public void getGameDetails(@ModelAttribute("gameId") Long gameId,HttpServletResponse response) throws Exception {

        GameRecord game = gameService.getGame(gameId);
        //UI'a gönder
        response.getWriter().write(JsonHandler.convertToJSON(game));

    }

}
