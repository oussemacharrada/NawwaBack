package com.dev.nawwa.controller.api;

import com.dev.nawwa.dto.CategoryDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface CategoryApi {
    @PostMapping(value = "/category/create" , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    CategoryDto save(@RequestBody CategoryDto dto);
    @GetMapping(value = "/category/{idCategory}",produces = MediaType.APPLICATION_JSON_VALUE)
        CategoryDto findById(@PathVariable("idCategory") Long id);
    @DeleteMapping(value = "/category/delete/{idCategory}", produces = MediaType.APPLICATION_JSON_VALUE)
    void delete(@PathVariable("idCategory")Long id);
    @GetMapping(value = "/category/all",produces = MediaType.APPLICATION_JSON_VALUE)
    List<CategoryDto> findAll();

}
