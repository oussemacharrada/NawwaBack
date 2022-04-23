package com.dev.nawwa.controller.api;

import com.dev.nawwa.dto.LocationDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface LocationApi {
    @PostMapping(value = "/location/create" , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    LocationDto save(@RequestBody LocationDto dto);
    @GetMapping(value = "/location/{idLocation}",produces = MediaType.APPLICATION_JSON_VALUE)
    LocationDto findById(@PathVariable("idLocation") Long id);
    @DeleteMapping(value = "/location/delete/{idLocation}", produces = MediaType.APPLICATION_JSON_VALUE)
    void delete(@PathVariable("idLocation")Long id);
    @GetMapping(value = "/location/all",produces = MediaType.APPLICATION_JSON_VALUE)
    List<LocationDto> findAll();
}
