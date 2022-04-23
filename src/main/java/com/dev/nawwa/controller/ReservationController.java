package com.dev.nawwa.controller;


import com.dev.nawwa.controller.api.ReservationApi;
import com.dev.nawwa.dto.ReservationDto;
import com.dev.nawwa.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReservationController implements ReservationApi {


    @Autowired

    ReservationService reservationService;

    @Autowired

    public ReservationController(ReservationService reservationService){
        this.reservationService = reservationService;
    }
    @Override
    public ReservationDto save(ReservationDto dto,Long id) {
        return reservationService.save(dto,id);
    }

    @Override
    public ReservationDto findById(Long id) {
        return reservationService.findOne(id);
    }

    @Override
    public void delete(Long id) {
    reservationService.delete(id);
    }

    @Override
    public List<ReservationDto> findAll() {
        return reservationService.findAll();
    }
}
