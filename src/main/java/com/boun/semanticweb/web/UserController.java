package com.boun.semanticweb.web;

import com.boun.semanticweb.base.JsonHandler;
import com.boun.semanticweb.model.User;
import com.boun.semanticweb.service.SecurityService;
import com.boun.semanticweb.service.UserService;
import com.boun.semanticweb.validator.UserValidator;
import com.google.gson.Gson;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

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

        securityService.autologin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "redirect:/welcome";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcome(Model model) {
        return "welcome";
    }
    
    /**
     * this is a simple example of how system API's will work.
     * you can control using http://localhost:8080/LinkedLanguage/getUserName?userId=5
     * @param userId
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/getUserName", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public void getUserName(@ModelAttribute("userId") String userId,HttpServletResponse response)
			throws Exception {
		
		String resultJson = JsonHandler.convertToJSON(userService.findByUserId(Long.parseLong(userId)));
		
		response.getWriter().write(resultJson);

	}
}
