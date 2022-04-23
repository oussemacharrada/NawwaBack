package com.dev.nawwa.service;


import com.dev.nawwa.domain.Category;
import com.dev.nawwa.dto.RatingDto;
import com.dev.nawwa.dto.ServiceDto;

import java.util.List;

public interface ServiceService {

    ServiceDto findById(Long id);
    ServiceDto save(ServiceDto dto);
    void delete(Long id);
    List<ServiceDto> findAll();
    List<ServiceDto> findByNameLike(String name);

    List<ServiceDto> findByName(String name);

    List<ServiceDto> findByCategory(Category category);

    List<ServiceDto> findByCategoryId(Long categoryId);

    ServiceDto findByIdAndCategoryId(Long id, Long categoryId);

}
