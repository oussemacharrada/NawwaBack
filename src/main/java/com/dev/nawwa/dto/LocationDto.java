package com.dev.nawwa.dto;

import com.dev.nawwa.domain.Location;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LocationDto {
    private Long id;
    @JsonIgnore
    private ReservationDto reservation;
    private String country;
    private String city;
    private String street;
    private String number;
    private double longitude;
    private double latitude;
    static public LocationDto  fromEntity(Location Location){
        if(Location==null){
            return null;
        }
        return LocationDto.builder()
                .id(Location.getId())
                .country(Location.getCountry())
                .city(Location.getCity())
                .street(Location.getStreet())
                .number(Location.getNumber())
                .longitude(Location.getLongitude())
                .latitude(Location.getLatitude())
                .build();
    }
   static public Location toEntity(LocationDto locationDto) {
        if(locationDto == null){
            return null;
        }

        Location location = new Location();
        location.setId(locationDto.getId());
        location.setCountry(locationDto.getCountry());
        location.setCity(locationDto.getCity());
        location.setStreet(locationDto.getStreet());
        location.setNumber(locationDto.getNumber());
        location.setLatitude(locationDto.getLatitude());
        location.setLongitude(locationDto.getLongitude());
        return location;
    }
}
