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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

        Word word = wordService.getRandomWord();
        Game game = gameService.startGame(word.getId());
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

        CommonUserOperations.removeTheGameFromSession();

        response.getWriter().write(JsonHandler.convertToJSON(gameUsers));

    }

}
