package com.udacity.jwdnd.course1.cloudstorage.services.business;

import com.udacity.jwdnd.course1.cloudstorage.data.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.data.model.Note;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    /**
     * Inserts a new note in the Notes database
     *
     * @param note Note entity
     * @return Rows affected by the insertion or -1 in case insertion failed
     */
    public int insertNote(Note note) {
        try {
            return noteMapper.insertNote(note);
        } catch (PersistenceException e) {
            System.out.println("Error inserting note: " + e.getMessage());
        }
        return -1;
    }

    /**
     * Retrieves a list of notes from database by userId
     *
     * @param userId Id of the current logged in user
     * @return returns a list of notes or null if the userId doesn't exist
     */
    public List<Note> getNotesByUser(int userId) {
        try {
            return noteMapper.getNotesByUser(userId);
        } catch (PersistenceException e) {
            System.out.println("Error retrieving notes for user with id: " + userId + ". Error: " + e.getMessage());
        }
        return null;
    }

    /**
     * Retrieves a note with a specific Id
     *
     * @param noteId The id of the requested note
     * @return Note object
     */
    public Note getNoteById(int noteId) {
        try {
            return noteMapper.getNoteById(noteId);
        } catch (PersistenceException e) {
            System.out.println("Error retrieving note with id: " + noteId + ". Error: " + e.getMessage());
        }
        return null;
    }

    public void deleteNote(int noteId) {
        try {
            noteMapper.deleteNote(noteId);
        } catch (PersistenceException e) {
            System.out.println("Error retrieving note with id: " + noteId + ". Error: " + e.getMessage());
        }
    }
}
