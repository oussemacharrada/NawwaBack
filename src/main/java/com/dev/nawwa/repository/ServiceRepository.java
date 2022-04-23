package com.dev.nawwa.repository;

import com.dev.nawwa.domain.Category;
import com.dev.nawwa.domain.Services;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Services,Long> {
    List<Services> findByNameLike(String name);

    List<Services> findByName(String name);

    List<Services> findByCategory(Category category);

    List<Services> findByCategoryId(Long categoryId);

    Services findByIdAndCategoryId(Long id, Long categoryId);

        List<Services> findByCategoryName(String categoryName);
}
