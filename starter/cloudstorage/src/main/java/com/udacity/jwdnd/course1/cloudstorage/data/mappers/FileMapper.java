package com.udacity.jwdnd.course1.cloudstorage.data.mappers;

import com.udacity.jwdnd.course1.cloudstorage.data.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Insert("INSERT INTO FILES(userId, fileName, contentType, fileSize, fileData) VALUES(#{userId}, #{fileName}, #{contentType}, #{fileSize}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    void insert(File file);

    @Select("SELECT * FROM FILES WHERE userId = #{userId}")
    List<File> getFilesByUser(int userId);

    @Select("SELECT * FROM FILES WHERE fileName = #{fileName}")
    File getFileByName(String fileName);

    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    File getFileById(int fileId);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    void deleteFile(int fileId);
}
