package com.udacity.jwdnd.course1.cloudstorage.data.mappers;

import com.udacity.jwdnd.course1.cloudstorage.data.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @Insert("INSERT INTO CREDENTIALS(userId, url, username, key, password) VALUES(#{userId}, #{url}, #{username}, #{key}, #{password})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insertCredentials(Credential credential);

    @Select("SELECT * FROM CREDENTIALS WHERE userId = #{userId}")
    List<Credential> getCredentialsByUser(int userId);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialId = #{credentialId}")
    Credential getCredentialById(int noteId);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialId = #{credentialId}")
    void deleteCredential(int credentialId);

    @Update("UPDATE CREDENTIALS SET userId=#{userId}, url=#{url}, username=#{username}, key=#{key}, password=#{password} WHERE credentialId = #{credentialId}")
    void updateCredential(Credential credentialId);
}
