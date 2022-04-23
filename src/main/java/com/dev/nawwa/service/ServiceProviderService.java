package com.dev.nawwa.service;

import com.dev.nawwa.dto.ServiceProviderProfileDto;

import java.util.List;

public interface ServiceProviderService {
    ServiceProviderProfileDto findById(Long id);


    ServiceProviderProfileDto save(ServiceProviderProfileDto dto);

    void delete(Long id);
    List<ServiceProviderProfileDto> findAll();
    ServiceProviderProfileDto  findByFirstNameAndLastName(String namePart1, String namePart2);
}
