package com.dev.nawwa.service;

import com.dev.nawwa.dto.LocationDto;
import com.dev.nawwa.dto.CategoryDto;

import java.util.List;

public interface LocationService {
    LocationDto findById(Long id);


    LocationDto save(LocationDto dto);

    void delete(Long id);
    List<LocationDto> findAll();
}
