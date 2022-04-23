package com.dev.nawwa.service;


import com.dev.nawwa.dto.CategoryDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {


    CategoryDto findById(Long id);


    CategoryDto save(CategoryDto dto);

    void delete(Long id);
    List<CategoryDto> findAll();
    CategoryDto findByName(String name);

}
