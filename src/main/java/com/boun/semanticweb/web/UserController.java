package com.boun.semanticweb.web;

import com.boun.semanticweb.base.CommonUserOperations;
import com.boun.semanticweb.base.JsonHandler;
import com.boun.semanticweb.model.User;
import com.boun.semanticweb.service.GameService;
import com.boun.semanticweb.service.SecurityService;
import com.boun.semanticweb.service.UserService;
import com.boun.semanticweb.validator.UserValidator;
import java.security.Principal;
import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private GameService gameService;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.save(userForm);

        //securityService.autologin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "redirect:/";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout,HttpServletRequest request) {
        
        System.out.println("buraya girdi");
        System.out.println("error vales" + error);
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null){
            model.addAttribute("message", "You have been logged out successfully.");
        }
        
        HttpSession session = request.getSession();
        session.invalidate();
            
        return "login";
    }

    @RequestMapping(value = {"/afterLogin"}, method = RequestMethod.GET)
    public String welcome(Model model,HttpServletRequest request,HttpServletResponse response,Principal principal) {
        System.out.println("after logine girdi");
        HttpSession session = request.getSession();
        User dbUser = userService.findByUsername(principal.getName());
        session.setAttribute("user", dbUser);
        session.setAttribute("username", (dbUser.getUsername()).toUpperCase());
        session.setAttribute("userId", dbUser.getId());
        session.setAttribute("userType", dbUser.getUserType());
        session.setMaxInactiveInterval(30*60);
        
        return "redirect:/welcome";
    }
    
    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcome(Model model) {
        
        return "welcome";
    }
    
    /**
     * this is a simple example of how system API's will work.
     * you can control using http://localhost:8080/LinkedLanguage/getUser?userId=5
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/getUserScore", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public void getUserScore(HttpServletResponse response)
			throws Exception {
		
		String resultJson = JsonHandler.convertToJSON(userService.findByUserId(CommonUserOperations.getUserId()).getTotalScore());
		
		response.getWriter().write(resultJson);

	}


    @RequestMapping(value = "/getUserGames", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public void getUserGames(HttpServletResponse response)throws Exception {

        String resultJson = JsonHandler.convertToJSON(gameService.getUserGames(CommonUserOperations.getUserId()));

        response.getWriter().write(resultJson);

    }

}
