package com.dev.nawwa.service.impl;

import com.dev.nawwa.domain.Category;
import com.dev.nawwa.domain.Reservation;
import com.dev.nawwa.domain.Services;
import com.dev.nawwa.dto.CategoryDto;
import com.dev.nawwa.dto.ReservationDto;
import com.dev.nawwa.dto.ServiceDto;
import com.dev.nawwa.repository.ReservationRepository;
import com.dev.nawwa.service.ReservationService;
import com.dev.nawwa.repository.ServiceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ReservationServiceImpl implements ReservationService {
    public ReservationRepository reservationRepository;

    public ServiceRepository serviceRepository ;
    @Autowired
    public ReservationServiceImpl(    ReservationRepository reservationRepository, ServiceRepository serviceRepository){
        this.reservationRepository = reservationRepository;
        this.serviceRepository = serviceRepository;
    }
    @Override
    public ReservationDto findOne(Long id) {
        if(id == null ){
            log.error("id is null");
            return null;
        }
        Optional<Reservation> reservation = reservationRepository.findById(id);
        ReservationDto dto = ReservationDto.fromEntity(reservation.get());
        return Optional.of(dto).orElseThrow(() -> new EntityNotFoundException("Rating provider not found"));
    }

    @Override
    public ReservationDto save(ReservationDto dto,Long ServiceId) {
        Optional<Services> service = serviceRepository.findById(ServiceId);
        ServiceDto serviceDto = ServiceDto.fromEntity(service.get());
        dto.setService(serviceDto);
        return ReservationDto.fromEntity(
                reservationRepository.save(ReservationDto.toEntity(dto))
        );
    }

    @Override
    public void delete(Long id) {
            reservationRepository.deleteById(id);
    }

    @Override
    public List<ReservationDto> findAll() {
        return reservationRepository.findAll().stream()
                .map(ReservationDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservationDto> findByClient() {
        return null;
    }

    @Override
    public List<ReservationDto> findByService() {
        return null;
    }


    @Override
    public ReservationDto findbyCategoryName(String categoryName) {
        return null;
    }

    @Override
    public ReservationDto findByServiceName(String serviceName) {
        return null;
    }

    @Override
    public ReservationDto findByServiceId(Long serviceId) {
        return null;
    }

    @Override
    public ReservationDto findByProfileId(Long id) {
        return null;
    }
}
