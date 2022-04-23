package com.dev.nawwa.service;

import com.dev.nawwa.domain.Services;
import com.dev.nawwa.dto.RatingDto;

import java.util.List;

public interface RatingService {
    RatingDto findOne(Long id);


    RatingDto save(RatingDto dto,Long ServiceId );

    void delete(Long id);
    List<RatingDto> findAll();
    List<RatingDto> findByClient();
    List<RatingDto> findByService(Services services);

    List<RatingDto> findByServiceId(Long Serviced);

    RatingDto findByUserAndServiceId(Long id, Long serviceId);
}
