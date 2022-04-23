package com.dev.nawwa.service;

import java.io.IOException;
import java.util.List;

import com.dev.nawwa.domain.User;
import org.springframework.web.multipart.MultipartFile;

import com.dev.nawwa.exception.domain.EmailExistException;
import com.dev.nawwa.exception.domain.EmailNotFoundException;
import com.dev.nawwa.exception.domain.UserNotFoundException;
import com.dev.nawwa.exception.domain.UsernameExistException;

public interface UserService
{
	User register(String userName, String email) throws UserNotFoundException, UsernameExistException, EmailExistException;
	
	List<User> getUsers();
	
	User findByUserName(String userName);
	
	User findUserByEmail(String email);
	
	User addUser( String username, String email, String role, boolean isNonLocked, boolean isActive, MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException;
	
	User updateUser(String currentUsername, String newUsername, String newEmail, String role, boolean isNonLocked, boolean isActive, MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException;
	
	void deleteUser(Long id);
	
	void resetPassword(String email) throws EmailNotFoundException;
	
	User updateProfileImage(String username, MultipartFile profileFile) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException;
}
