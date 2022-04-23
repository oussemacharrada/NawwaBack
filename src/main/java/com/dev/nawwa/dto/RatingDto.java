package com.dev.nawwa.dto;

import com.dev.nawwa.domain.Rating;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RatingDto {
    private Long id;
    @JsonIgnore
    private ServiceDto service;
    private int rating;
    @JsonIgnore
    private UserProfileDto client;
    private String comment;
    public static RatingDto  fromEntity(Rating rating){
        if(rating==null){
            return null;
        }
        return RatingDto.builder()
                .id(rating.getId())
                .rating(rating.getRating())
                .comment(rating.getComment()    )
                .build();
    }   
    public static Rating toEntity(RatingDto ratingDto) {
        if(ratingDto == null){
            return null;
        }

        Rating rating = new Rating();
        rating.setId(ratingDto.getId());
        rating.setRating(ratingDto.getRating());
        rating.setComment(ratingDto.getComment());
        return rating;
    }
}
