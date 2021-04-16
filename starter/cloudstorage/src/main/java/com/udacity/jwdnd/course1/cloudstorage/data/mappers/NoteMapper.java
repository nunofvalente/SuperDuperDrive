package com.udacity.jwdnd.course1.cloudstorage.data.mappers;

import com.udacity.jwdnd.course1.cloudstorage.data.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Insert("INSERT INTO NOTES(userId, noteTitle, noteDescription) VALUES(#{userId}, #{noteTitle}, #{noteDescription})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insertNote(Note note);

    @Select("SELECT * FROM NOTES WHERE userId = #{userId}")
    List<Note> getNotesByUser(int userId);

    @Select("SELECT * FROM NOTES WHERE noteId = #{noteId}")
    Note getNoteById(int noteId);

    @Delete("DELETE FROM NOTES WHERE noteId = #{noteId}")
    void deleteNote(int noteId);
}
