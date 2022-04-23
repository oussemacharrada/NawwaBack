package com.dev.nawwa.service.impl;

import com.dev.nawwa.domain.Location;
import com.dev.nawwa.dto.LocationDto;
import com.dev.nawwa.repository.LocationRepository;
import com.dev.nawwa.service.LocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LocationServiceImpl implements LocationService {
    LocationRepository locationRepository;
    @Autowired
    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository ;
    }
    @Override
    public LocationDto findById(Long id) {
        if(id == null ){
            log.error("id is null");
            return null;
        }
        Optional<Location> location = locationRepository.findById(id);
        LocationDto locationDto = LocationDto.fromEntity(location.get());
        return Optional.of(locationDto).orElseThrow(() -> new EntityNotFoundException("Location not found"));
    }

    @Override
    public LocationDto save(LocationDto dto) {
        return LocationDto.fromEntity(
                locationRepository.save(LocationDto.toEntity(dto))
        );
    }

    @Override
    public void delete(Long id) {
        if(id == null ){
            log.error("id is null");
            return ;
        }
        locationRepository.deleteById(id);
    }

    @Override
    public List<LocationDto> findAll() {
        return locationRepository.findAll().stream()
                .map(LocationDto::fromEntity)
                .collect(Collectors.toList());
    }
}
