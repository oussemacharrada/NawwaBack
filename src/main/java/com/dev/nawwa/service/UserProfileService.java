package com.dev.nawwa.service;

import com.dev.nawwa.dto.UserProfileDto;

import java.util.List;

public interface UserProfileService {
    UserProfileDto findById(Long id);


    UserProfileDto save(UserProfileDto dto);

    void delete(Long id);
    List<UserProfileDto> findAll();
    UserProfileDto findByFirstNameAndLastName(String namePart1, String namePart2);
}
