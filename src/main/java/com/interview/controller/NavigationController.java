package com.interview.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NavigationController {

    @GetMapping("/")
    public String getHome(Model model) {
        model.addAttribute("activePage", "info");
        return "home";
    }

    @GetMapping("/login")
	public String login() {
		return "/user/login";
	}
    
    @GetMapping("/Campaigns")
    public String getCampaign(Model model) {
        model.addAttribute("activePage", "Campaigns");
        return "redirect:/campaigns";
    }

    @GetMapping("/LineItems")
    public String getLineItem(Model model) {
        model.addAttribute("activePage", "LineItem");
        return "redirect:/campaigns";
    }

}
