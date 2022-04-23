package com.dev.nawwa.service.impl;

import com.dev.nawwa.domain.ServiceProviderProfile;
import com.dev.nawwa.domain.User;
import com.dev.nawwa.dto.ServiceProviderProfileDto;
import com.dev.nawwa.repository.ServiceProviderRepository;
import com.dev.nawwa.repository.UserRepository;
import com.dev.nawwa.service.ServiceProviderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ServiceProivderServiceImpl implements ServiceProviderService {




    public ServiceProviderRepository serviceProviderRepository;
    @Autowired
    public UserRepository userRepository;
    @Autowired
    public ServiceProivderServiceImpl(    ServiceProviderRepository serviceProviderRepository){
        this.serviceProviderRepository = serviceProviderRepository;

    }
    @Override
    public ServiceProviderProfileDto findById(Long id) {
        if(id == null ){
            log.error("id is null");
            return null;
        }
        Optional<ServiceProviderProfile> serviceProviderProfile = serviceProviderRepository.findById(id);
        ServiceProviderProfileDto dto = ServiceProviderProfileDto.fromEntity(serviceProviderProfile.get());
        return Optional.of(dto).orElseThrow(() -> new EntityNotFoundException("Service provider not found"));
    }

    @Override
    public ServiceProviderProfileDto save(ServiceProviderProfileDto dto) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userRepository.findUserByUserName(auth.getName());
            dto.setAccount(user);
            return ServiceProviderProfileDto.fromEntity(
                    serviceProviderRepository.save(ServiceProviderProfileDto.toEntity(dto)));
    }



    @Override
    public void delete(Long id) {
        if(id == null ){
            log.error("id is null");
            return ;
        }
        serviceProviderRepository.deleteById(id);
    }

    @Override
    public List<ServiceProviderProfileDto> findAll() {
        return serviceProviderRepository.findAll().stream()
                .map(ServiceProviderProfileDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public ServiceProviderProfileDto findByFirstNameAndLastName(String namePart1, String namePart2) {
        return null;
    }
}
