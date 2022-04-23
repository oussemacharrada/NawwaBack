package com.dev.nawwa.dto;

import com.dev.nawwa.domain.Services;
import com.dev.neo.supportportal.domain.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ServiceDto {

    private Long id;
    @JsonIgnore
    private List<ReservationDto> reservation = new ArrayList<>();
    private String name;
    @JsonIgnore
    private CategoryDto category;
    private String serviceImageUrl;
    @JsonIgnore
    private List<RatingDto> ratings = new ArrayList<>();
    private double serviceCost;
    private String description;
    private ServiceProviderProfileDto serviceProvider;

    public static ServiceDto  fromEntity(Services services){
        if(services==null){
            return null;
        }
        return ServiceDto.builder()
                .id(services.getId())
                .name(services.getName())
                .serviceImageUrl(services.getServiceImageUrl())
                .serviceCost(services.getServiceCost())
                .description(services.getDescription())
                .serviceProvider(services.getServiceProvider())
                .build();
    }
    public static Services toEntity(ServiceDto serviceDto) {
        if(serviceDto == null){
            return null;
        }

        Services service = new Services();
        service.setId(serviceDto.getId());
        service.setName(serviceDto.getName());
        service.setDescription(serviceDto.getDescription());
        service.setServiceImageUrl(serviceDto.getServiceImageUrl());
        service.setServiceCost(serviceDto.getServiceCost());
        service.setServiceProvider(serviceDto.getServiceProvider());

        return service;
    }
}
