package com.interview.controller;

import com.interview.model.User;
import com.interview.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class LoginController {

	@Autowired
	private UserService userService;

    @ModelAttribute("user")
    public User user() {
        return new User();
    }

    @GetMapping
	public String showRegistrationForm() {
		return "/user/registration";
	}

    @PostMapping
	public String registerUserAccount(@ModelAttribute("user") User user) {
		userService.save(user);
		return "redirect:/registration?success";
	}

}
