package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.data.dto.FileDTO;
import com.udacity.jwdnd.course1.cloudstorage.data.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.business.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.business.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.business.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.business.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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
        model.addAttribute("credentialList", credentialService.getCredentialsByUser(userId));
        model.addAttribute("fileList", fileService.getFilesByUser(userId));
        return "home";
    }

    @GetMapping("/home/deleteNote/{noteId}")
    public String handleDeleteNote(@PathVariable("noteId") String noteId) {
        int id = Integer.parseInt(noteId);
        noteService.deleteNote(id);
        return "redirect:/home/result?entity=note&id=" + id;
    }

    @GetMapping("/home/deleteCredential/{credentialId}")
    public String handleCredential(@PathVariable("credentialId") String credentialId) {
        int id = Integer.parseInt(credentialId);
        credentialService.deleteCredential(id);
        return "redirect:/home/result?entity=credential&id=" + id;
    }

    @GetMapping("/home/deleteFile/{fileId}")
    public String handleFile(@PathVariable("fileId") String fileId) {
        int id = Integer.parseInt(fileId);
        fileService.deleteFile(id);
        return "redirect:/home/result?entity=file&id="+id;
    }

    @GetMapping("/home/downloadFile/{fileId}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable("fileId") String fileId) {
        int id = Integer.parseInt(fileId);
        File file = fileService.getFileById(id);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"");
        httpHeaders.add("Cache-control", "no-cache, no-store, must-revalidate");
        httpHeaders.add("Pragma", "no-cache");
        httpHeaders.add("Expires", "0");

        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(new ByteArrayResource(file.getFileData()));
    }

    @ModelAttribute("fileDTO")
    public FileDTO getFileDTO() {return new FileDTO();}

    @PostMapping("/success_logout")
    public String handleLogout() {
        return "redirect:/login?logout";
    }
}
