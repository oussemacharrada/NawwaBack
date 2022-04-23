package com.dev.nawwa.controller;


import com.dev.nawwa.controller.api.CategoryApi;
import com.dev.nawwa.dto.CategoryDto;
import com.dev.nawwa.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController implements CategoryApi {

    @Autowired
    private CategoryService categoryService;


    @Autowired
    public CategoryController(
CategoryService categoryService
    ){
        this.categoryService = categoryService;
    }


    @Override
    public CategoryDto save(CategoryDto dto) {
        return categoryService.save(dto);
    }

    @Override
    public CategoryDto findById(Long id) {
        return categoryService.findById(id);
    }

    @Override
    public void delete(Long id) {
        categoryService.delete(id);
    }

    @Override
    public List<CategoryDto> findAll() {
        return categoryService.findAll();
    }
}
