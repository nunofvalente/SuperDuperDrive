package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.services.business.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.business.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.business.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.business.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
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

    @GetMapping("/home")
    public String getHomePage(Authentication authentication, Model model) {
        int userId = userService.getLoggedUserId(authentication);
        model.addAttribute("notesList", noteService.getNotesByUser(userId));
        return "home";
    }

    @GetMapping("/home/deleteNote/{noteId}")
    public String handleDeleteNote(@PathVariable("noteId") String noteId) {
        int id = Integer.parseInt(noteId);
        noteService.deleteNote(id);
        return "redirect:/home/result?entity=note&id=" + id;
    }

    @PostMapping("/success_logout")
    public String handleLogout() {
        return "redirect:/login?logout";
    }
}
