package com.dev.nawwa.controller.api;

import com.dev.nawwa.dto.ServiceDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface ServiceApi {
    @PostMapping(value = "/service/create" , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ServiceDto save(@RequestBody ServiceDto dto);
    @GetMapping(value = "/service/{idService}",produces = MediaType.APPLICATION_JSON_VALUE)
    ServiceDto findById(@PathVariable("idService") Long id);
    @DeleteMapping(value = "/service/delete/{idService}", produces = MediaType.APPLICATION_JSON_VALUE)
    void delete(@PathVariable("idService")Long id);
    @GetMapping(value = "/service/all",produces = MediaType.APPLICATION_JSON_VALUE)
    List<ServiceDto> findAll();
}
