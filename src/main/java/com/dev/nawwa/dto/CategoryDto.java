package com.dev.nawwa.dto;

import com.dev.nawwa.domain.Category;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
@Builder
public class CategoryDto {
    private Long id;
    private String name;

    private String categoryImageUrl;
    @JsonIgnore
    private List<ServiceDto> services = new ArrayList<>();



    public static CategoryDto  fromEntity(Category category){
        if(category==null){
            return null;
        }
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .categoryImageUrl(category.getCategoryImageUrl())
                .build();
    }
    public  static Category toEntity(CategoryDto categoryDto) {
        if(categoryDto == null){
            return null;
        }

        Category category = new Category();
        category.setId(categoryDto.getId());
        category.setName(categoryDto.getName());
        category.setCategoryImageUrl(categoryDto.getCategoryImageUrl());
        return category;
    }
}
