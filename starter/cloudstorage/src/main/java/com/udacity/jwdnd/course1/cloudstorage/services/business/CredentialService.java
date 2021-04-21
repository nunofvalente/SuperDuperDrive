package com.udacity.jwdnd.course1.cloudstorage.services.business;

import com.udacity.jwdnd.course1.cloudstorage.data.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.data.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.security.EncryptionService;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

@Service
public class CredentialService {

    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    /**
     * Inserts a new credential in the Credentials database
     *
     * @param credential Credential entity
     * @return Rows affected by the insertion or -1 in case insertion failed
     */
    public int insertCredential(Credential credential) {
        try {
            String encodedKey = getEncodedKey();
            credential.setKey(encodedKey);
            String encodedPassword = encryptionService.encryptValue(credential.getPassword(), credential.getKey());
            credential.setPassword(encodedPassword);
            return credentialMapper.insertCredentials(credential);
        } catch (PersistenceException e) {
            System.out.println("Error inserting credential: " + e.getMessage());
        }
        return -1;
    }

    /**
     * Retrieves a list of credentials from database by userId
     *
     * @param userId Id of the current logged in user
     * @return returns a list of credentials or null if the userId doesn't exist
     */
    public List<Credential> getCredentialsByUser(int userId) {
        try {
            return credentialMapper.getCredentialsByUser(userId);
        } catch (PersistenceException e) {
            System.out.println("Error retrieving credentials for user with id: " + userId + ". Error: " + e.getMessage());
        }
        return Collections.emptyList();
    }

    /**
     * Retrieves a credential with a specific Id
     *
     * @param credentialId The id of the requested note
     * @return Credential object
     */
    public Credential getCredentialById(int credentialId) {
        try {
            return credentialMapper.getCredentialById(credentialId);
        } catch (PersistenceException e) {
            System.out.println("Error retrieving note with id: " + credentialId + ". Error: " + e.getMessage());
        }
        return null;
    }

    /**
     * Deletes a credential with a specific credentialId
     *
     * @param credentialId The id of the credential to delete
     */
    public void deleteCredential(int credentialId) {
        try {
             credentialMapper.deleteCredential(credentialId);
        } catch (PersistenceException e) {
            System.out.println("Error retrieving credential with id: " + credentialId + ". Error: " + e.getMessage());
        }
    }

    /**
     * Deletes a credential with a specific noteId
     *
     * @param credential Credential we want to update
     */
    public void updateCredential(Credential credential) {
        try {
            String encodedPassword = encryptionService.encryptValue(credential.getPassword(), credential.getKey());
            credential.setPassword(encodedPassword);
            credentialMapper.updateCredential(credential);
        } catch (PersistenceException e) {
            System.out.println("Error updating credential with id: " + credential.getCredentialId() + ". Error: " + e.getMessage());
        }
    }

    private String getEncodedKey() {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        return Base64.getEncoder().encodeToString(key);
    }
}
