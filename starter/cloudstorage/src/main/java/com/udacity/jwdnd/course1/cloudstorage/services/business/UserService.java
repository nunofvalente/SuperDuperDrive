package com.udacity.jwdnd.course1.cloudstorage.services.business;

import com.udacity.jwdnd.course1.cloudstorage.data.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.data.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.security.HashService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {

    private final HashService hashService;
    private final UserMapper userMapper;

    public UserService(HashService hashService, UserMapper userMapper) {
        this.hashService = hashService;
        this.userMapper = userMapper;
    }

    /**
     * Inserts a new User entry in the database
     *
     * @param user User entity
     * @return returns rows affected by the insertion
     */
    public int createUser(User user) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedValue = hashService.getHashedValue(user.getPassword(), encodedSalt);

        return userMapper.insertUser(new User(null, user.getUsername(), encodedSalt, hashedValue, user.getFirstName(), user.getLastName()));
    }


    /**
     * Retrieves a user by username from database
     *
     * @param username Username of the user
     * @return User entity
     */
    public User getUser(String username) {
        return userMapper.getUserByUsername(username);
    }


    /**
     * This method returns the current logged user Id
     *
     * @param authentication To determine if there is a logged in user
     * @return userId if there is a user logged in
     */
    public int getLoggedUserId(Authentication authentication) {
        String username = authentication.getName();

        if(username != null) {
            User user = userMapper.getUserByUsername(username);
            return user.getUserId();
        }

        return -1;
    }

    /**
     * Validates if the username has been taken upon registration
     *
     * @param username Username of the user
     * @return void
     */
    public boolean isUsernameTaken(String username) {
        return userMapper.userExists(username);
    }
}
