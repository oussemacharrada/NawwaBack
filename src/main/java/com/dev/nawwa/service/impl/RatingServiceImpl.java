package com.dev.nawwa.service.impl;

import com.dev.nawwa.domain.Rating;
import com.dev.nawwa.domain.Services;
import com.dev.nawwa.dto.RatingDto;
import com.dev.nawwa.dto.ServiceDto;
import com.dev.nawwa.repository.RatingRepository;
import com.dev.nawwa.repository.ServiceRepository;

import com.dev.nawwa.service.RatingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RatingServiceImpl implements RatingService {
        public RatingRepository ratingRepository;

        public ServiceRepository serviceRepository ;
        @Autowired
        public RatingServiceImpl(    RatingRepository ratingRepository, ServiceRepository serviceRepository){
            this.ratingRepository = ratingRepository;
            this.serviceRepository = serviceRepository;

        }
    @Override
    public RatingDto findOne(Long id) {
        if(id == null ){
            log.error("id is null");
            return null;
        }
        Optional<Rating> rating = ratingRepository.findById(id);
        RatingDto dto = RatingDto.fromEntity(rating.get());
        return Optional.of(dto).orElseThrow(() -> new EntityNotFoundException("Rating provider not found"));
    }

    @Override
    public RatingDto save(RatingDto dto, Long ServiceId) {
        Optional<Services> service = serviceRepository.findById(ServiceId);
        ServiceDto serviceDto = ServiceDto.fromEntity(service.get());
        dto.setService(serviceDto);
        return RatingDto.fromEntity(
                ratingRepository.save(RatingDto.toEntity(dto))
        );
    }

    @Override
    public void delete(Long id) {
        ratingRepository.deleteById(id);

    }

    @Override
    public List<RatingDto> findAll() {
        return ratingRepository.findAll().stream()
                .map(RatingDto::fromEntity)
                .collect(Collectors.toList());

    }

    @Override
    public List<RatingDto> findByClient() {
        return null;
    }

    @Override
    public List<RatingDto> findByService(Services services) {
        if(services == null ){
            log.error("id is null");
            return null;
        }
            return ratingRepository.findByService(services).stream()
                    .map(RatingDto::fromEntity)
                    .collect(Collectors.toList());
    }

    @Override
        public List<RatingDto> findByServiceId(Long Serviced) {
        return ratingRepository.findByServiceId(Serviced).stream()
                .map(RatingDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public RatingDto findByUserAndServiceId(Long id, Long serviceId) {
        return null;
    }
}
