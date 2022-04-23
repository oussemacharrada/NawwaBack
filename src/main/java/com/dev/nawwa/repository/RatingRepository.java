package com.dev.nawwa.repository;

import com.dev.nawwa.domain.Rating;
import com.dev.nawwa.domain.Services;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository

public interface RatingRepository extends JpaRepository<Rating,Long> {
    List<Rating> findByService(Services services);

    List<Rating> findByServiceId(Long Serviced);

    Rating findByUserAndServiceId(Long id, Long serviceId);
}
