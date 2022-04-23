package com.dev.nawwa.controller;

import com.dev.nawwa.controller.api.ServiceProviderProfileApi;
import com.dev.nawwa.dto.ServiceProviderProfileDto;
import com.dev.nawwa.service.ServiceProviderService;
import org.springframework.beans.factory.annotation.Autowired;

public class ServiceProviderProfileController implements ServiceProviderProfileApi {
    @Autowired
    private ServiceProviderService serviceProviderService;


    @Autowired
    public ServiceProviderProfileController(
            ServiceProviderService serviceProviderService
    ){
        this.serviceProviderService = serviceProviderService;
    }


    @Override
    public ServiceProviderProfileDto save(ServiceProviderProfileDto dto) {
        return serviceProviderService.save(dto);
    }
}
