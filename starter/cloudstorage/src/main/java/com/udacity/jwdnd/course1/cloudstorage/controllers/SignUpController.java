package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.data.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.business.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignUpController {

    private final UserService userService;

    public SignUpController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    String getSignUpPage(@ModelAttribute User user, Model model) {
        model.addAttribute("success", false);
        return "signup";
    }

    @PostMapping
    String createUserAndRedirect(@ModelAttribute("user") User user, Model model) {
        String errorMessage = "Username already exists!";

        if(!userService.isUsernameTaken(user.getUsername())) {
            model.addAttribute("success", true);
            userService.createUser(user);
        } else {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", errorMessage);
        }
        return "signup";
    }
}
