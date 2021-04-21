package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.data.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.data.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.business.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.business.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.business.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.business.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ResultController {

    private final CredentialService credentialService;
    private final NoteService noteService;
    private final UserService userService;
    private final FileService fileService;

    public ResultController(CredentialService credentialService, NoteService noteService, UserService userService, FileService fileService) {
        this.credentialService = credentialService;
        this.noteService = noteService;
        this.userService = userService;
        this.fileService = fileService;
    }

    @GetMapping("/home/result")
    public String showResult(@RequestParam("entity") String entity, @RequestParam("id") int id, Model model) {
        switch (entity) {
            case "note":
                if (noteService.getNoteById(id) != null) {
                    model.addAttribute("error", true);
                }
                model.addAttribute("success", true);
                break;
            case "file":
                if (fileService.getFileById(id) != null) {
                    model.addAttribute("error", true);
                }
                model.addAttribute("success", true);
                break;
            case "credential":
                if (credentialService.getCredentialById(id) != null) {
                    model.addAttribute("error", true);
                }
                model.addAttribute("success", true);
                break;
        }
        return "result";
    }

    @PostMapping("/home/result")
    public String handleState(Authentication authentication, @RequestParam("entity") String entity, @ModelAttribute Note note, @ModelAttribute Credential credential, Model model
    ) {
        int userId = userService.getLoggedUserId(authentication);
        switch (entity) {
            case "note":
                processNote(userId, note, model);
                break;
            case "credential":
                processCredentials(userId, credential, model);
                break;
        }
        return "result";
    }

    @PostMapping("/home/result/uploadFile")
    public String uploadFile(Authentication authentication, @ModelAttribute("file") MultipartFile file, Model model) {
        int userId = userService.getLoggedUserId(authentication);

        if(file.isEmpty()) {
            String errorMsg = "No file to upload, please add a file!";
            model.addAttribute("errorMsg", true);
            model.addAttribute("msg", errorMsg);
        } else if(fileService.getFileByName(file.getOriginalFilename()) != null) {
            String errorMsg = "File with name " + file.getOriginalFilename() + " already exists!";
            model.addAttribute("errorMsg", true);
            model.addAttribute("msg", errorMsg);
        } else {
            model.addAttribute("success", true);
            fileService.insertFile(file, userId);
        }

        return "result";
    }


    private void processCredentials(Integer userId, Credential credential, Model model) {
        credential.setUserId(userId);

        if (credential.getCredentialId() == null) {
            credentialService.insertCredential(credential);
        } else {
            credentialService.updateCredential(credential);
        }

        if (credentialService.getCredentialById(credential.getCredentialId()) != null) {
            model.addAttribute("success", true);
        }
    }

    private void processNote(Integer userId, Note note, Model model) {
        note.setUserId(userId);
        if (note.getNoteId() == null) {
            noteService.insertNote(note);
        } else {
            noteService.updateNote(note);
        }

        if (noteService.getNoteById(note.getNoteId()) != null) {
            model.addAttribute("success", true);
        }
    }
}
