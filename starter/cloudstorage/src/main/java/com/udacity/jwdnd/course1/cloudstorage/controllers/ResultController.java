package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.data.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.business.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.business.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ResultController {

    private final  NoteService noteService;
    private final UserService userService;

    public ResultController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @GetMapping("/home/result")
    public String showResult(@RequestParam("entity") String entity, @RequestParam("id") int id, Model model) {
        switch(entity) {
            case "note":
                model.addAttribute("error", false);
                if (noteService.getNoteById(id) != null) {
                    model.addAttribute("success", false);
                    System.out.print("Inside condition");
                    return "result";
                }
                model.addAttribute("success", true);
                break;
            case "file":

                break;
            case "credential":

                break;
        }

        return "result";
    }

    @PostMapping("/home/result")
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
