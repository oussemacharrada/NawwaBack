package com.dev.nawwa.service;

import com.dev.nawwa.dto.ReservationDto;

import java.util.List;

public interface ReservationService {
    ReservationDto findOne(Long id);
    ReservationDto save(ReservationDto dto,Long SeviceId);
    void delete(Long id);
    List<ReservationDto> findAll();
    List<ReservationDto> findByClient();
    List<ReservationDto> findByService();
    ReservationDto findbyCategoryName(String categoryName);
    ReservationDto  findByServiceName(String serviceName);
    ReservationDto findByServiceId(Long serviceId);
    ReservationDto  findByProfileId(Long id);
}
