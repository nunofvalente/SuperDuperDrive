package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.data.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.business.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.business.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("home/result")
public class ResultController {

    private final  NoteService noteService;
    private final UserService userService;

    public ResultController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @GetMapping
    public String getResultPage(Authentication authentication, @ModelAttribute Note note, Model model) {
        return "redirect:/login";
    }

    @PostMapping
    public String handleState(Authentication authentication, @ModelAttribute Note note, Model model) {
        int userId = userService.getLoggedUserId(authentication);
        note.setUserId(userId);
        noteService.insertNote(note);

        //assign initial value for attribute error
        model.addAttribute("error", false);

        if(noteService.getNoteById(note.getNoteId()) != null) {
            model.addAttribute("success", true);
        }

        return "result";
    }
}
