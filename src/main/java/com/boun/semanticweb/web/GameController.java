/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boun.semanticweb.web;

import com.boun.semanticweb.base.JsonHandler;
import com.boun.semanticweb.model.Word;
import com.boun.semanticweb.service.WordService;
import javax.servlet.http.HttpServletResponse;
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

    @RequestMapping(value = "/playGame", method = RequestMethod.GET)
    public String registration(Model model) {
        return "gamePlay";
    }
    

}
