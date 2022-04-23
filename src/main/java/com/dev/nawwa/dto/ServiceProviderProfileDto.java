package com.dev.nawwa.dto;

import com.dev.nawwa.domain.User;
import com.dev.nawwa.domain.ServiceProviderProfile;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ServiceProviderProfileDto {

    private Long id;
    private User account;

    private String firstname;

    private String lastname;

    private String email;

    private String phoneNumber;

    private String pictureLink;

    private String about;

    private String CertificatePictureLink;  

    private Long numberOfbids;
    @JsonIgnore
    private List<ServiceDto> services = new ArrayList<>();
    public static ServiceProviderProfileDto  fromEntity(ServiceProviderProfile serviceProviderProfile){
        if(serviceProviderProfile==null){
            return null;
        }
        return ServiceProviderProfileDto.builder()
                .id(serviceProviderProfile.getId())
                .firstname(serviceProviderProfile.getFirstname())
                .lastname(serviceProviderProfile.getLastname())
                .email(serviceProviderProfile.getEmail())
                .phoneNumber(serviceProviderProfile.getPhoneNumber())
                .pictureLink(serviceProviderProfile.getPictureLink())
                .about(serviceProviderProfile.getAbout())
                .CertificatePictureLink(serviceProviderProfile.getCertificatePictureLink())
                .numberOfbids(serviceProviderProfile.getNumberOfbids())
                .build();
    }
    public static ServiceProviderProfile toEntity(ServiceProviderProfileDto serviceProviderProfileDto) {
        if(serviceProviderProfileDto == null){
            return null;
        }

        ServiceProviderProfile serviceProviderProfile = new ServiceProviderProfile();
        serviceProviderProfile.setId(serviceProviderProfileDto.getId());
        serviceProviderProfile.setFirstname(serviceProviderProfileDto.getFirstname());
        serviceProviderProfile.setLastname(serviceProviderProfileDto.getLastname());
        serviceProviderProfile.setAbout(serviceProviderProfileDto.getAbout());
        serviceProviderProfile.setEmail(serviceProviderProfileDto.getEmail());
        serviceProviderProfile.setPhoneNumber(serviceProviderProfileDto.getPhoneNumber());
        serviceProviderProfile.setPictureLink(serviceProviderProfileDto.getPictureLink());
        serviceProviderProfile.setCertificatePictureLink(serviceProviderProfile.getCertificatePictureLink());
        serviceProviderProfile.setNumberOfbids(serviceProviderProfileDto.getNumberOfbids());






        return serviceProviderProfile;
    }

}
