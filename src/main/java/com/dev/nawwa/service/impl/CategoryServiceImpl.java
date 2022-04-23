package com.dev.nawwa.service.impl;

import com.dev.nawwa.domain.Category;
import com.dev.nawwa.repository.CategoryRepository;
import com.dev.nawwa.dto.CategoryDto;
import com.dev.nawwa.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    CategoryRepository categoryRepository;
    @Autowired
    public CategoryServiceImpl(    CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;

            }
    @Override
    public CategoryDto findById(Long id) {
        if(id == null ){
            log.error("id is null");
            return null;
        }
        Optional<Category>category = categoryRepository.findById(id);
        CategoryDto dto = CategoryDto.fromEntity(category.get());

        return Optional.of(dto).orElseThrow(() -> new EntityNotFoundException("Category not found"));
    }

    @Override
    public CategoryDto save(CategoryDto dto) {
     return CategoryDto.fromEntity(
             categoryRepository.save(CategoryDto.toEntity(dto))
     );
    }

    @Override
    public void delete(Long id) {
        if(id == null ){
            log.error("id is null");
            return ;
        }
categoryRepository.deleteById(id);
    }

    @Override
    public List<CategoryDto> findAll() {
       return categoryRepository.findAll().stream()
               .map(CategoryDto::fromEntity)
               .collect(Collectors.toList());
    }

    @Override
    public CategoryDto findByName(String name) {
        if(name == null ){
            log.error("name is null");
            return null;
        }
        Optional<Category> category = categoryRepository.findByName(name);
        CategoryDto dto = CategoryDto.fromEntity(category.get());

        return Optional.of(dto).orElseThrow(() -> new EntityNotFoundException("Category not found"));
    }
    public void update(CategoryDto dto){

    }
}
