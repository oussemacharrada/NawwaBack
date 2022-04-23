package com.dev.nawwa.repository;

import com.dev.nawwa.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ReservationRepository extends JpaRepository<Reservation,Long> {

}
