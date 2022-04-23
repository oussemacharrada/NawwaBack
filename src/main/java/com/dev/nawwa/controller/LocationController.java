package com.dev.nawwa.controller;


import com.dev.nawwa.controller.api.LocationApi;
import com.dev.nawwa.dto.LocationDto;
import com.dev.nawwa.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LocationController implements LocationApi {

    @Autowired
    LocationService locationService ;
    @Autowired
    public LocationController(LocationService locationService ){
        this.locationService = locationService;
    }



    @Override
    public LocationDto save(LocationDto dto) {
        return locationService.save(dto);
    }

    @Override
    public LocationDto findById(Long id) {
        return locationService.findById(id);
    }

    @Override
    public void delete(Long id) {
locationService.delete(id);
    }

    @Override
    public List<LocationDto> findAll() {
        return locationService.findAll();
    }
}
