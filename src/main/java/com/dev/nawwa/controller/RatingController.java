package com.dev.nawwa.controller;

import com.dev.nawwa.controller.api.RatingApi;
import com.dev.nawwa.dto.RatingDto;
import com.dev.nawwa.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RatingController implements RatingApi {
    @Autowired
    RatingService ratingService;
    @Autowired
    public RatingController(RatingService ratingService){
        this.ratingService = ratingService;
    }
    @Override
    public RatingDto save(RatingDto dto,Long id) {
        return ratingService.save(dto,id);
    }

    @Override
    public RatingDto findById(Long id) {
        return ratingService.findOne(id);
    }

    @Override
    public void delete(Long id) {
ratingService.delete(id);
    }

    @Override
    public List<RatingDto> findAll() {
        return ratingService.findAll();
    }
}
