package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.data.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.data.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.business.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.business.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.business.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.business.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final NoteService noteService;
    private final UserService userService;
    private final  FileService fileService;
    private final CredentialService credentialService;

    public HomeController(NoteService noteService, UserService userService, FileService fileService, CredentialService credentialService) {
        this.noteService = noteService;
        this.userService = userService;
        this.fileService = fileService;
        this.credentialService = credentialService;
    }

    @GetMapping
    public String getHomePage(Authentication authentication, Model model) {
        int userId = userService.getLoggedUserId(authentication);
        model.addAttribute("notesList", noteService.getNotesByUser(userId));
        return "home";
    }

    @PostMapping("/success_logout")
    public String handleLogout() {
        return "redirect:/login?logout";
    }
}
