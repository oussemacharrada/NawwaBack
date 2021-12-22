package com.backend.service;

import com.backend.domain.User;
import com.backend.exception.domain.EmailExistException;
import com.backend.exception.domain.UserNotFoundException;
import com.backend.exception.domain.UsernameExistException;

import java.util.List;

public interface UserService {

    User register(String username, String email) throws UserNotFoundException, UsernameExistException, EmailExistException;

    List<User> getUsers();

    User findUserByUsername(String username);

    User findUserByEmail(String email);
}
