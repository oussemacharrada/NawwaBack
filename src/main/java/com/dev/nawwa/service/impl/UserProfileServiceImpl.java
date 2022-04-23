package com.dev.nawwa.service.impl;

import com.dev.nawwa.service.UserProfileService;
import com.dev.nawwa.domain.User;
import com.dev.nawwa.dto.UserProfileDto;
import com.dev.nawwa.repository.UserProfileRepository;
import com.dev.nawwa.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserProfileServiceImpl implements UserProfileService {
    public UserProfileRepository userProfileRepository;
    @Autowired
    public UserRepository userRepository;
    @Autowired
    public UserProfileServiceImpl(    UserProfileRepository userProfileRepository){
        this.userProfileRepository = userProfileRepository;

    }
    @Override
    public UserProfileDto findById(Long id) {
        return null;
    }

    @Override
    public UserProfileDto save(UserProfileDto dto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findUserByUserName(auth.getName());
        dto.setAccount(user);
        return UserProfileDto.fromEntity(
                userProfileRepository.save(UserProfileDto.toEntity(dto)));
    }



    @Override
    public void delete(Long id) {
        if(id == null ){
            log.error("id is null");
            return ;
        }
        userProfileRepository.deleteById(id);

    }

    @Override
    public List<UserProfileDto> findAll() {
        return userProfileRepository.findAll().stream()
                .map(UserProfileDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public UserProfileDto findByFirstNameAndLastName(String namePart1, String namePart2) {
        return null;
    }
}
