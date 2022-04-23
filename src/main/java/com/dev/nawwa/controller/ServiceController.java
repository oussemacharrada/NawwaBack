package com.dev.nawwa.controller;


import com.dev.nawwa.controller.api.ServiceApi;
import com.dev.nawwa.dto.ServiceDto;
import com.dev.nawwa.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ServiceController implements ServiceApi {

    @Autowired
    ServiceService serviceService;
    @Autowired
    public ServiceController(ServiceService serviceService){
        this.serviceService = serviceService;
    }

    @Override
    public ServiceDto save(ServiceDto dto) {
        return serviceService.save(dto);
    }

    @Override
    public ServiceDto findById(Long id) {
        return serviceService.findById(id);
    }

    @Override
    public void delete(Long id) {
    serviceService.delete(id);
    }

    @Override
    public List<ServiceDto> findAll() {
        return serviceService.findAll();
    }
}
