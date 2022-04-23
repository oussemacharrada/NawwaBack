package com.dev.nawwa.dto;

import com.dev.nawwa.domain.Reservation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;
@Data
@Builder
public class ReservationDto {
    private Long id;
    @JsonIgnore
    private UserProfileDto client;
    @JsonIgnore
    private ServiceDto service;
    @JsonIgnore
    private List<LocationDto> locations = new ArrayList<>();
    private String description;
    private DateTime serviceTime;
    public static ReservationDto  fromEntity(Reservation reservation){
        if(reservation==null){
            return null;
        }
        return ReservationDto.builder()
                .id(reservation.getId())
                .description(reservation.getDescription())
                .serviceTime(reservation.getServiceTime())
                .build();
    }
    public static Reservation toEntity(ReservationDto reservationDto) {
        if(reservationDto == null){
            return null;
        }

        Reservation reservation = new Reservation();
        reservation.setId(reservationDto.getId());
        reservation.setDescription(reservationDto.getDescription());
        reservation.setServiceTime(reservationDto.getServiceTime());
        return reservation;
    }



}
