package com.dev.nawwa.service.impl;

import com.dev.nawwa.domain.Category;
import com.dev.nawwa.domain.Services;
import com.dev.nawwa.dto.RatingDto;
import com.dev.nawwa.dto.ServiceDto;
import com.dev.nawwa.repository.CategoryRepository;
import com.dev.nawwa.repository.ServiceProviderRepository;
import com.dev.nawwa.repository.ServiceRepository;
import com.dev.nawwa.repository.UserRepository;
import com.dev.nawwa.service.ServiceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ServiceServiceImpl implements ServiceService {


    @Autowired
    private CategoryRepository categoryRepository;
    public ServiceRepository serviceRepository;
    @Autowired
    public UserRepository userRepository;
    @Autowired
    public ServiceProviderRepository serviceProviderRepository ;
    @Autowired
    public ServiceServiceImpl(    ServiceRepository serviceRepository){
        this.serviceRepository = serviceRepository;

    }
    @Override
    public ServiceDto findById(Long id) {
        if(id == null ){
            log.error("id is null");
            return null;
        }
        Optional<Services> services = serviceRepository.findById(id);
        ServiceDto dto = ServiceDto.fromEntity(services.get());
        return Optional.of(dto).orElseThrow(() -> new EntityNotFoundException("Service provider not found"));
    }

    @Override
    public ServiceDto save(ServiceDto dto){

        return ServiceDto.fromEntity(
                serviceRepository.save(ServiceDto.toEntity(dto)));
    }

    @Override
    public void delete(Long id) {
        if(id == null ){
            log.error("id is null");
            return ;
        }
        serviceRepository.deleteById(id);

    }

    @Override
    public List<ServiceDto> findAll() {
        return serviceRepository.findAll().stream()
                .map(ServiceDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<ServiceDto> findByNameLike(String name) {
        return null;
    }

    @Override
    public List<ServiceDto> findByName(String name) {
        return serviceRepository.findByName(name).stream()
                .map(ServiceDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<ServiceDto> findByCategory(Category category) {
        return serviceRepository.findByCategory(category).stream()
                .map(ServiceDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<ServiceDto> findByCategoryId(Long categoryId) {
        return serviceRepository.findByCategoryId(categoryId).stream()
                .map(ServiceDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public ServiceDto findByIdAndCategoryId(Long id, Long categoryId) {
        return null;
    }



}
