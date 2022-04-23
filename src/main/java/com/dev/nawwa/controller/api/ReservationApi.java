package com.dev.nawwa.controller.api;

import com.dev.nawwa.dto.ReservationDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface ReservationApi {
    @PostMapping(value = "/reservation/create/{idService}" , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ReservationDto save(@RequestBody ReservationDto dto,@PathVariable("idService")Long id);
    @GetMapping(value = "/reservation/{idReservation}",produces = MediaType.APPLICATION_JSON_VALUE)
    ReservationDto findById(@PathVariable("idReservation") Long id);
    @DeleteMapping(value = "/reservation/delete/{idReservation}", produces = MediaType.APPLICATION_JSON_VALUE)
    void delete(@PathVariable("idReservation")Long id);
    @GetMapping(value = "/reservation/all",produces = MediaType.APPLICATION_JSON_VALUE)
    List<ReservationDto> findAll();
}
