package com.dev.nawwa.dto;

import com.dev.nawwa.domain.User;
import com.dev.nawwa.domain.UserProfile;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
@Builder
public class UserProfileDto {

    private Long id;
    @JsonIgnore
    private User account;
    private String firstname;
    private String lastname;

    private String email;

    private String phoneNumber;

    private String pictureLink;

    private String about;

    @JsonIgnore
    private List<ReservationDto> reservations = new ArrayList<>();

    @JsonIgnore
    private List<RatingDto> ratings = new ArrayList<>();


    public static UserProfileDto  fromEntity(UserProfile userProfile){
        if(userProfile==null){
            return null;
        }
        return UserProfileDto.builder()
                .id(userProfile.getId())
                .firstname(userProfile.getFirstname())
                .lastname(userProfile.getLastname())
                .email(userProfile.getEmail())
                .phoneNumber(userProfile.getPhoneNumber())
                .pictureLink(userProfile.getPictureLink())
                .about(userProfile.getAbout())
                .build();
    }
    public static UserProfile toEntity(UserProfileDto userProfileDto) {
        if(userProfileDto == null){
            return null;
        }
        UserProfile userProfile = new UserProfile();
        userProfile.setId(userProfileDto.getId());
        userProfile.setFirstname(userProfileDto.getFirstname());
        userProfile.setLastname(userProfileDto.getLastname());
        userProfile.setPhoneNumber(userProfileDto.getPhoneNumber());
        userProfile.setPictureLink(userProfileDto.getPictureLink());
        userProfile.setAbout(userProfileDto.getAbout());
        return userProfile;
    }
}
