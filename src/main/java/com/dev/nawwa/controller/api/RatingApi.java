package com.dev.nawwa.controller.api;

import com.dev.nawwa.dto.RatingDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface RatingApi {
    @PostMapping(value = "/rating/create/{idService}" , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    RatingDto save(@RequestBody RatingDto dto,@PathVariable("idService") Long id);
    @GetMapping(value = "/rating/{idRating}",produces = MediaType.APPLICATION_JSON_VALUE)
    RatingDto findById(@PathVariable("idRating") Long id);
    @DeleteMapping(value = "/rating/delete/{idRating}", produces = MediaType.APPLICATION_JSON_VALUE)
    void delete(@PathVariable("idRating")Long id);
    @GetMapping(value = "/rating/all",produces = MediaType.APPLICATION_JSON_VALUE)
    List<RatingDto> findAll();
}
