package com.udacity.jwdnd.course1.cloudstorage.services.business;

import com.udacity.jwdnd.course1.cloudstorage.data.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.data.model.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class FileService {

    @Autowired
    private FileMapper fileMapper;

    /**
     * Inserts a new file into the database
     *
     * @param multiFile File we want to insert
     */

    public void insertFile(MultipartFile multiFile, int userId) {
        try {
            File file = new File(userId, multiFile.getOriginalFilename(), multiFile.getContentType(), String.valueOf(multiFile.getSize()), multiFile.getBytes());
            fileMapper.insert(file);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns all files related to a specific user
     *
     * @param userId Id of the authenticated user
     * @return a list of files belonging to that user
     */
    public List<File> getFilesByUser(int userId) {
        try {
            return fileMapper.getFilesByUser(userId);
        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns file witht the given id
     *
     * @param fileId Id of the file
     * @return a file with given id
     */
    public File getFileById(int fileId) {
        try {
            return fileMapper.getFileById(fileId);
        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns a file that contains the given name
     *
     * @param fileName Title of the file
     * @return a file with given name
     */
    public File getFileByName(String fileName) {
        try {
            return fileMapper.getFileByName(fileName);
        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns a specific file
     *
     * @param fileId id of the file to delete
     */
    public void deleteFile(int fileId) {
        try {
           fileMapper.deleteFile(fileId);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
