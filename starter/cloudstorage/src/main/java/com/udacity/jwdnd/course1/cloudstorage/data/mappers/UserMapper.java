package com.udacity.jwdnd.course1.cloudstorage.data.mappers;

import com.udacity.jwdnd.course1.cloudstorage.data.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Insert("INSERT INTO USERS(username, salt, password, firstName, lastName) VALUES(#{username}, #{salt}, #{password} , #{firstName}, #{lastName})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int insertUser(User user);

    @Select("SELECT * FROM USERS WHERE username = #{username}")
    User getUserByUsername(String username);

    @Select("SELECT EXISTS(SELECT * FROM USERS WHERE username = #{username})")
    boolean userExists(String username);
}
